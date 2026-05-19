package com.setlisto.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.DataException;
import com.setlisto.dao.PagoDAO;
import com.setlisto.dao.TicketDAO;
import com.setlisto.dao.ZonaEventoDAO;
import com.setlisto.model.Pago;
import com.setlisto.model.TicketDTO;
import com.setlisto.model.ZonaEvento;
import com.setlisto.service.ReservaService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class ReservaServiceImpl implements ReservaService {

    private static final Logger logger = LogManager.getLogger(ReservaServiceImpl.class.getName());
    private static final Long PAYMENT_STATUS_APPROVED = 2L;
    private static final Long TICKET_TYPE_GENERAL = 1L;

    private ZonaEventoDAO eventZoneDAO = new ZonaEventoDAO();
    private PagoDAO pagoDAO = new PagoDAO();
    private TicketDAO ticketDAO = new TicketDAO();

    @Override
    public List<TicketDTO> comprarZona(Long eventZoneId, Long customerId, int cantidad, Long paymentMethodId, Long currencyId) throws ServiceException {
        // Validaciones Sin tocar la base de datos aun
        if (eventZoneId == null || customerId == null || cantidad <= 0 || paymentMethodId == null || currencyId == null) {
            throw new ServiceException("Datos de reserva incompletos.");
        }

        // Pre-cálculo y generación de datos fuera de la transacción
        LocalDateTime fechaActual = LocalDateTime.now();
        String codigoPago = "SIM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        List<String> codigosTickets = new ArrayList<>(cantidad);
        for (int i = 0; i < cantidad; i++) {
            codigosTickets.add("TCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        Connection c = null;
        boolean commit = false;
        try {
            c = JDBCUtils.getConnection();

            // Lectura de estado (Aún sin abrir la transacción de escritura)
            ZonaEvento zone = eventZoneDAO.findById(c, eventZoneId);
            if (zone == null) {
                throw new ServiceException("La zona seleccionada no existe.");
            }
            if (zone.getCapacidadDisponible() == null || zone.getCapacidadDisponible() < cantidad) {
                throw new ServiceException("No hay plazas suficientes disponibles en la zona seleccionada.");
            }
            if (zone.getPrecioBase() == null) {
                throw new ServiceException("La zona seleccionada no tiene precio configurado.");
            }

            BigDecimal montoTotal = zone.getPrecioBase().multiply(new BigDecimal(cantidad));

            c.setAutoCommit(false); // Inicio de transaccion

            // Modificación atómica (Aquí es donde la DB empieza a bloquear filas)
            boolean reserved = eventZoneDAO.reserveCapacity(c, eventZoneId, cantidad);
            if (!reserved) {
                throw new ServiceException("No fue posible reservar plazas en la zona seleccionada.");
            }

            // Preparar e Insertar el Pago
            Pago pago = new Pago();
            pago.setClienteId(customerId);
            pago.setEstadoPagoId(PAYMENT_STATUS_APPROVED);
            pago.setFechaPago(fechaActual);
            pago.setMetodoPagoId(paymentMethodId);
            pago.setMonedaId(currencyId);
            pago.setMonto(montoTotal);
            pago.setCodigoTransaccion(codigoPago);

            Pago creado = pagoDAO.create(c, pago);
            if (creado == null || creado.getId() == null) {
                throw new ServiceException("No fue posible registrar el pago.");
            }

            // Preparar e Insertar Tickets
            List<TicketDTO> tickets = new ArrayList<>(cantidad);
            for (int i = 0; i < cantidad; i++) {
                TicketDTO ticket = new TicketDTO();
                ticket.setCodigo(codigosTickets.get(i));
                ticket.setPrecio(zone.getPrecioBase());
                ticket.setFechaCompra(fechaActual);
                ticket.setPagoId(creado.getId());
                ticket.setTipoTicketId(TICKET_TYPE_GENERAL);
                ticket.setEventZoneId(eventZoneId);
                
                Long ticketId = ticketDAO.create(c, ticket);
                ticket.setId(ticketId);
                tickets.add(ticket);
            }

            commit = true; // todo salio bien
            return tickets;
        } catch (DataException e) {
            logger.error("Error de persistencia al comprar zona con id {}, cliente id {}, cantidad {}, metodo de pago id {}, moneda id {}: {}", 
                    eventZoneId, customerId, cantidad, paymentMethodId, currencyId, e.getMessage());
            throw new ServiceException(e);
        } catch (Exception e) {
            logger.error("Comprando zona {} para cliente {}: {}", eventZoneId, customerId, e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            JDBCUtils.close(c, commit);
        }
    }
}
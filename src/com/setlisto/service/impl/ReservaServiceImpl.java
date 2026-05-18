package com.setlisto.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.EventZoneDAO;
import com.setlisto.dao.PagoDAO;
import com.setlisto.dao.TicketDAO;
import com.setlisto.model.EventZone;
import com.setlisto.model.Pago;
import com.setlisto.model.TicketDTO;
import com.setlisto.service.ReservaService;
import com.setlisto.utils.JDBCUtils;

public class ReservaServiceImpl implements ReservaService {

	private static final Logger logger = LogManager.getLogger(ReservaServiceImpl.class.getName());
	private static final Long PAYMENT_STATUS_APPROVED = 2L;
	private static final Long TICKET_TYPE_GENERAL = 1L;

	private EventZoneDAO eventZoneDAO = new EventZoneDAO();
	private PagoDAO pagoDAO = new PagoDAO();
	private TicketDAO ticketDAO = new TicketDAO();

	@Override
	public List<TicketDTO> comprarZona(Long eventZoneId, Long customerId, int cantidad, Long paymentMethodId, Long currencyId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			if (eventZoneId == null || customerId == null || cantidad <= 0 || paymentMethodId == null || currencyId == null) {
				throw new Exception("Datos de reserva incompletos.");
			}

			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);

			EventZone zone = eventZoneDAO.findById(c, eventZoneId);
			if (zone == null) {
				throw new Exception("La zona seleccionada no existe.");
			}
			if (zone.getAvailableCapacity() == null || zone.getAvailableCapacity() < cantidad) {
				throw new Exception("No hay plazas suficientes disponibles en la zona seleccionada.");
			}
			if (zone.getBasePrice() == null) {
				throw new Exception("La zona seleccionada no tiene precio configurado.");
			}

			boolean reserved = eventZoneDAO.reserveCapacity(c, eventZoneId, cantidad);
			if (!reserved) {
				throw new Exception("No fue posible reservar plazas en la zona seleccionada.");
			}

			Pago pago = new Pago();
			pago.setClienteId(customerId);
			pago.setEstadoPagoId(PAYMENT_STATUS_APPROVED);
			pago.setFechaPago(LocalDateTime.now());
			pago.setMetodoPagoId(paymentMethodId);
			pago.setMonedaId(currencyId);
			pago.setMonto(zone.getBasePrice().multiply(new BigDecimal(cantidad)));
			pago.setCodigoTransaccion("SIM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

			Pago creado = pagoDAO.create(c, pago);
			if (creado == null || creado.getId() == null) {
				throw new Exception("No fue posible registrar el pago.");
			}

			List<TicketDTO> tickets = new ArrayList<TicketDTO>();
			for (int i = 0; i < cantidad; i++) {
				TicketDTO ticket = new TicketDTO();
				ticket.setCodigo("TCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
				ticket.setPrecio(zone.getBasePrice());
				ticket.setFechaCompra(LocalDateTime.now());
				ticket.setPagoId(creado.getId());
				ticket.setTipoTicketId(TICKET_TYPE_GENERAL);
				ticket.setEventZoneId(eventZoneId);
				Long ticketId = ticketDAO.create(c, ticket);
				ticket.setId(ticketId);
				tickets.add(ticket);
			}

			commit = true;
			return tickets;
		} catch (Exception e) {
			logger.error("Comprando zona {} para cliente {}: {}", eventZoneId, customerId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}

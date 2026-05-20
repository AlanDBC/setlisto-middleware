package com.setlisto.service;

import java.math.BigDecimal;

import com.setlisto.criteria.PagoCriteria;
import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión de transacciones financieras y estados de pago.
 */
public interface PagoService {

    /**
     * Registra un nuevo pago dentro de la aplicacion
     * 
     * @param pago a registrar
     * @return Pago registrado
     * @throws ServiceException
     */
    public Pago processPayment(Pago pago) throws ServiceException;

    /**
     * Recupera la información detallada de un pago por su ID.
     * 
     * @param id del pago
     * @return Pago encontrado
     * @throws ServiceException
     */
    public PagoDTO findById(Long id) throws ServiceException;

    /**
     * Actualiza el estado de un pago
     * 
     * @param pagoId
     * @param estadoId
     * @return true si se actualizo, false en caso contrario
     * @throws ServiceException
     */
    public boolean updateStatus(Long pagoId, Long estadoId) throws ServiceException;

    /**
     * Realiza una busqueda por criterios
     * 
     * @param criteria
     * @param page desde
     * @param size tamaño de pagina
     * @return Lista (Results<>) de pagos correspondientes a los criterios
     * @throws ServiceException
     */
    public Results<PagoDTO> findByCriteria(PagoCriteria criteria, int page, int size) throws ServiceException;

    /**
     * Calcula el monto total pagado por un cliente
     * 
     * @param clienteId
     * @return Precio total pagado
     * @throws ServiceException
     */
    public BigDecimal getTotalApprovedByCustomer(Long clienteId) throws ServiceException;
}
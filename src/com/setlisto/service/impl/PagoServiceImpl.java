package com.setlisto.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PagoCriteria;
import com.setlisto.dao.PagoDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PagoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class PagoServiceImpl implements PagoService {

	private static final Logger logger = LogManager.getLogger(PagoServiceImpl.class.getName());

	private PagoDAO pagoDAO = null;

	public PagoServiceImpl() {
		this.pagoDAO = new PagoDAO();
	}

	@Override
	public Pago processPayment(Pago pago) throws ServiceException { 
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Pago creado = pagoDAO.create(c, pago);
			commit = true;
			return creado;
		} catch (DataException e) {
			logger.error("Error de persistencia al crear el pago {}: {}", pago, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Creando pago {}: {}", pago, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public PagoDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			PagoDTO pago = pagoDAO.findById(c, id);
			commit = true;
			return pago;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar pago con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Actualiza el estado de un pago existente
	 */
	@Override
	public boolean updateStatus(Long paymentId, Long statusId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean actualizado = pagoDAO.updateStatus(c, paymentId, statusId);
			commit = true;
			return actualizado;
		} catch (DataException e) {
			logger.error("Error de persistencia al cambiar estado de pago con id {} y estatus id {}: {}", paymentId, statusId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Actualizando estado de pago con id {}: {}", paymentId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<PagoDTO> findByCriteria(PagoCriteria criteria, int from, int pageSize) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<PagoDTO> resultados = pagoDAO.findByCriteria(c, criteria, from , pageSize);
			commit = true;
			return resultados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar con criteria {} : {}", criteria, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por criteria {}: {}", criteria, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Retorna la suma de todos los pagos con estado pagado hechos por un cliente
	 */
	@Override
	public BigDecimal getTotalApprovedByCustomer(Long customerId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			BigDecimal total = pagoDAO.getTotalPagadoByClienteId(c, customerId);
			commit = true;
			return total;
		} catch (DataException e) {
			logger.error("Error de persistencia al calculcar total aprobado de cliente con id {} : {}", customerId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Trayendo total pagado por cliente con id {}: {}", customerId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
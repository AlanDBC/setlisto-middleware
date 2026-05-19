package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.DataException;
import com.setlisto.dao.EstadoPagoDAO;
import com.setlisto.model.EstadoPago;
import com.setlisto.service.EstadoPagoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class EstadoPagoServiceImpl implements EstadoPagoService {

	private static final Logger logger = LogManager.getLogger(EstadoPagoServiceImpl.class.getName());

	private EstadoPagoDAO estadoPagoDAO = null;

	public EstadoPagoServiceImpl() {
		this.estadoPagoDAO = new EstadoPagoDAO();
	}

	@Override
	public EstadoPago findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EstadoPago estado = estadoPagoDAO.findById(c, id);
			commit = true;
			return estado;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar estado con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Recupera todos los estados de pago (PENDING, APPROVED, REJECTED, etc.).
	 */
	@Override
	public List<EstadoPago> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<EstadoPago> estados = estadoPagoDAO.findAll(c);
			commit = true;
			return estados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los estados: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos",e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
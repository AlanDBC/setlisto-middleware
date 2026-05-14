package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.EstadoPagoDAO;
import com.setlisto.model.EstadoPago;
import com.setlisto.service.EstadoPagoService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de estados de pago.
 */
public class EstadoPagoServiceImpl implements EstadoPagoService {

	private static final Logger logger = LogManager.getLogger(EstadoPagoServiceImpl.class.getName());

	private EstadoPagoDAO estadoPagoDAO = null;

	public EstadoPagoServiceImpl() {
		this.estadoPagoDAO = new EstadoPagoDAO();
	}

	/**
	 * Recupera el estado de pago delegando al DAO.
	 */
	@Override
	public EstadoPago findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EstadoPago estado = estadoPagoDAO.findById(c, id);
			commit = true;
			return estado;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Recupera todos los estados de pago (PENDING, APPROVED, REJECTED, etc.).
	 */
	@Override
	public List<EstadoPago> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<EstadoPago> estados = estadoPagoDAO.findAll(c);
			commit = true;
			return estados;
		} catch (Exception e) {
			logger.error("Buscando todos",e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
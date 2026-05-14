package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.MetodoPagoDAO;
import com.setlisto.model.MetodoPago;
import com.setlisto.service.MetodoPagoService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de métodos de pago.
 */
public class MetodoPagoServiceImpl implements MetodoPagoService {

	private static final Logger logger = LogManager.getLogger(MetodoPagoServiceImpl.class.getName());

	private MetodoPagoDAO metodoPagoDAO = null;

	public MetodoPagoServiceImpl() {
		this.metodoPagoDAO = new MetodoPagoDAO();
	}

	/**
	 * Recupera el método de pago utilizando el DAO.
	 */
	@Override
	public MetodoPago findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			MetodoPago metodo = metodoPagoDAO.findById(c, id);
			commit = true;
			return metodo;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Obtiene todos los métodos de pago (VISA, PayPal, Bizum, etc.) definidos en la tabla payment_method.
	 */
	@Override
	public List<MetodoPago> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<MetodoPago> metodos = metodoPagoDAO.findAll(c);
			commit = true;
			return metodos;
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
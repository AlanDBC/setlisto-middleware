package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.MonedaPagoDAO;
import com.setlisto.model.MonedaPago;
import com.setlisto.service.MonedaPagoService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de monedas de pago.
 */
public class MonedaPagoServiceImpl implements MonedaPagoService {

	private static final Logger logger = LogManager.getLogger(MonedaPagoServiceImpl.class.getName());

	private MonedaPagoDAO monedaPagoDAO = null;

	public MonedaPagoServiceImpl() {
		this.monedaPagoDAO = new MonedaPagoDAO();
	}

	/**
	 * Recupera la moneda delegando al DAO, que consulta la tabla payment_currency.
	 */
	@Override
	public MonedaPago findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			MonedaPago moneda = monedaPagoDAO.findById(c, id);
			commit = true;
			return moneda;
		} catch (Exception e) {
			logger.error("Buscando id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Obtiene todas las monedas registradas (EUR, USD, GBP, etc.).
	 */
	@Override
	public List<MonedaPago> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<MonedaPago> monedas = monedaPagoDAO.findAll(c);
			commit = true;
			return monedas;
		} catch (Exception e) {
			logger.error("Buscando todas", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
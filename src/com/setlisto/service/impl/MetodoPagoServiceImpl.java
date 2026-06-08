package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.MetodoPagoDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.MetodoPago;
import com.setlisto.service.MetodoPagoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class MetodoPagoServiceImpl implements MetodoPagoService {

	private static final Logger logger = LogManager.getLogger(MetodoPagoServiceImpl.class.getName());

	private MetodoPagoDAO metodoPagoDAO = null;

	public MetodoPagoServiceImpl() {
		this.metodoPagoDAO = new MetodoPagoDAO();
	}

	@Override
	public MetodoPago findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			MetodoPago metodo = metodoPagoDAO.findById(c, id);
			commit = true;
			return metodo;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar metodo de pago con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<MetodoPago> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<MetodoPago> metodos = metodoPagoDAO.findAll(c);
			commit = true;
			return metodos;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los metodos de pago : {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
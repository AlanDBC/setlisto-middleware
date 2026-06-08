package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.TipoTicketDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.TipoTicket;
import com.setlisto.service.ServiceException;
import com.setlisto.service.TipoTicketService;
import com.setlisto.utils.JDBCUtils;

public class TipoTicketServiceImpl implements TipoTicketService {

	private static final Logger logger = LogManager.getLogger(TipoTicketServiceImpl.class.getName());

	private TipoTicketDAO tipoTicketDAO = null;
	
	public TipoTicketServiceImpl() {
		this.tipoTicketDAO = new TipoTicketDAO();
	}

	@Override
	public TipoTicket findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			TipoTicket tipo = tipoTicketDAO.findById(c,id);
			commit = true;
			return tipo;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar tipo de ticket con Id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Obtiene todos los tipos de tickets (General, VIP, Pista, Grada) definidos.
	 */
	@Override
	public List<TipoTicket> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<TipoTicket> tipos = tipoTicketDAO.findAll(c);
			commit = true;
			return tipos;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los tipos de ticket: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos: {}", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
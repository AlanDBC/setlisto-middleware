package com.setlisto.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.dao.EventoMusicalDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.Results;
import com.setlisto.service.EventoMusicalService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class EventoMusicalServiceImpl implements EventoMusicalService {

	private static final Logger logger = LogManager.getLogger(EventoMusicalServiceImpl.class.getName());

	private EventoMusicalDAO eventoMusicalDAO = null;

	public EventoMusicalServiceImpl() {
		this.eventoMusicalDAO = new EventoMusicalDAO();
	}

	@Override
	public EventoMusicalDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EventoMusicalDTO evento = eventoMusicalDAO.findById(c, id);
			commit = true;
			return evento;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar evento con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<EventoMusicalDTO> findByCriteria(EventoMusicalCriteria criteria, int from, int pageSize) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<EventoMusicalDTO> resultados = eventoMusicalDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar evento con criteria {}: {}", criteria, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por criteria {}: {}", criteria, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public EventoMusicalDTO create(EventoMusicalDTO evento) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			EventoMusicalDTO creado = eventoMusicalDAO.create(c, evento);
			commit = true;
			return creado;
		} catch (DataException e) {
			logger.error("Error de persistencia al crear evento {}: {}", evento, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Creando evento {}: {}", evento, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public void update(EventoMusicalDTO evento) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			eventoMusicalDAO.update(c, evento);
			commit = true;
			return;
		} catch (DataException e) {
			logger.error("Error de persistencia al modificar evento {}: {}", evento, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Modificando evento {}: {}", evento, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public void delete(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			eventoMusicalDAO.delete(c, id);
			commit = true;
			return;
		} catch (DataException e) {
			logger.error("Error de persistencia al eliminar evento con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Eliminando evento con id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
package com.setlisto.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PlazaEnEventoCriteria;
import com.setlisto.dao.PlazaEnEventoDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaEnEventoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class PlazaEnEventoServiceImpl implements PlazaEnEventoService {

	private static final Logger logger = LogManager.getLogger(PlazaEnEventoServiceImpl.class.getName());

	private PlazaEnEventoDAO plazaEnEventoDAO = null;

	public PlazaEnEventoServiceImpl() {
		this.plazaEnEventoDAO = new PlazaEnEventoDAO();
	}

	@Override
	public PlazaEnEventoDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			PlazaEnEventoDTO plaza = plazaEnEventoDAO.findById(c, id);
			commit = true;
			return plaza;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar por ID {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<PlazaEnEventoDTO> findByCriteria(PlazaEnEventoCriteria criteria, int from, int pageSize) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<PlazaEnEventoDTO> resultados = plazaEnEventoDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar plazas en evento con criteria {}: {}", criteria , e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por criteria {}: {}", criteria, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Comprueba si una plaza esta disponible
	 */
	@Override
	public boolean isSeatAvailable(Long plazaEnEventoId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean disponible = plazaEnEventoDAO.isAvailable(c, plazaEnEventoId);
			commit = true;
			return disponible;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar disponibilidad de plaza con id {} : {}", plazaEnEventoId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Viendo disponibilidad de plaza con id {}: {}", plazaEnEventoId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean updateStatus(Long plazaEnEventoId, Long statusId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean cambiado = plazaEnEventoDAO.updateStatus(c, plazaEnEventoId, statusId);
			commit = true;
			return cambiado;
		} catch (DataException e) {
			logger.error("Error de persistencia al cambiar estado de plaza con id {} y estado id {}: {}", plazaEnEventoId, statusId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Cambiando estado de plaza con id {}: {}", plazaEnEventoId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Trae el total de plazas disponibles para el evento
	 */
	@Override
	public int getAvailableCount(Long eventId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			int disponibles = plazaEnEventoDAO.countAvailableByEvent(c, eventId);
			commit = true;
			return disponibles;
		} catch (DataException e) {
			logger.error("Error de persistencia al calcular total de plazas disponibles para evento con id {}: {}", eventId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Viendo plazas disponibles de evento con id {}: {}", eventId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
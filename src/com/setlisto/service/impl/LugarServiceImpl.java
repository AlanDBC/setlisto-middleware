package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.LugarCriteria;
import com.setlisto.dao.LugarDAO;
import com.setlisto.model.DataException;
import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.model.Results;
import com.setlisto.service.LugarService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de gestión de lugares.
 */
public class LugarServiceImpl implements LugarService {

	private static final Logger logger = LogManager.getLogger(LugarServiceImpl.class.getName());

	private LugarDAO lugarDAO = null;

	public LugarServiceImpl() {
		this.lugarDAO = new LugarDAO();
	}

	@Override
	public LugarDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			LugarDTO lugar = lugarDAO.findById(c, id);
			commit = true;
			return lugar;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar lugar con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando lugar por id{}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<LugarDTO> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<LugarDTO> lugares = lugarDAO.findAll(c);
			commit = true;
			return lugares;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los lugares: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<LugarDTO> findByCriteria(LugarCriteria criteria, int from, int pageSize) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<LugarDTO> resultados = lugarDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar con criteria {}: {}", criteria, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por criteria {}: {}", criteria, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Lugar create(Lugar lugar) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Lugar creado = lugarDAO.create(c, lugar);
			commit = true;
			return creado;
		} catch (DataException e) {
			logger.error("Error de persistencia al crear lugar {}: {}", lugar, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Creando {}: {}", lugar, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public void update(Lugar lugar) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			lugarDAO.update(c, lugar);
			commit = true;
			return;
		} catch (DataException e) {
			logger.error("Error de persistencia al modificar lugar {}: {}", lugar, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Modificando {}: {}", lugar, e.getMessage(), e);
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
			lugarDAO.delete(c, id);
			commit = true;
			return; 
		} catch (DataException e) {
			logger.error("Error de persistencia al eliminar lugar con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Eliminando con id {}: {}", id,  e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
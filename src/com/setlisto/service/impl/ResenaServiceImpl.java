package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.ResenaDAO;
import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;
import com.setlisto.service.ResenaService;
import com.setlisto.utils.JDBCUtils;

public class ResenaServiceImpl implements ResenaService {

	private static final Logger logger = LogManager.getLogger(ResenaServiceImpl.class.getName());

	private ResenaDAO resenaDAO = null;

	public ResenaServiceImpl() {
		this.resenaDAO = new ResenaDAO();
	}

	@Override
	public Resena create(Resena resena) throws Exception {
	    Connection c = null;
	    boolean commit = false;   
	    try {
	        c = JDBCUtils.getConnection();
	        c.setAutoCommit(false); 
	        
	        Resena existente = resenaDAO.findByEventAndCustomer(c, resena.getEventoId(), resena.getClienteId());

	        if (existente != null) {
	        	throw new Exception("El usuario ya ha reseñado el evento");
	        }
	        
	        resenaDAO.create(c, resena);
	        
	        commit = true;
	        return resena;

	    } catch (Exception e) {
	        logger.error("Creando reseña para evento con id {} y cliente con id{}: {}", 
	                     resena.getEventoId(), resena.getClienteId(), e.getMessage());
	        throw e;
	    } finally {
	        JDBCUtils.close(c, commit);
	    }
	}

	/**
	 * Permite modificar estrellas, comentario o el flag de favorito 
	 */
	@Override
	public boolean update(Resena resena) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean modificado = resenaDAO.edit(c, resena);
			commit = true;
			return modificado;
		} catch (Exception e) {
			logger.error("Modificando {}: {}", resena, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean delete(Long eventoId, Long usuarioId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean borrado = resenaDAO.delete(c, eventoId, usuarioId);
			commit = true;
			return borrado;
		} catch (Exception e) {
			logger.error("Eliminando reseña, id evento {}, id de usuario {}: {}", eventoId, usuarioId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public ResenaDTO findById(Long eventoId, Long usuarioId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			ResenaDTO resena = resenaDAO.findByEventAndCustomer(c, eventoId, usuarioId);
			commit = true;
			return resena;
		} catch (Exception e) {
			logger.error("Buscando reseña con id evento {}, id de usuario {}: {}", eventoId, usuarioId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<ResenaDTO> findByMusicalEvent(Long eventoId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<ResenaDTO> resenas = resenaDAO.findByMusicalEvent(c, eventoId);
			commit = true;
			return resenas;
		} catch (Exception e) {
			logger.error("Buscando reseñas id evento: {} : {}", eventoId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}

	}

	@Override
	public List<ResenaDTO> findByCustomer(Long usuarioId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<ResenaDTO> resenas = resenaDAO.findByCustomer(c, usuarioId);
			commit = true;
			return resenas;
		} catch (Exception e) {
			logger.error("Buscando reseñas, id usuario {}: {}", usuarioId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
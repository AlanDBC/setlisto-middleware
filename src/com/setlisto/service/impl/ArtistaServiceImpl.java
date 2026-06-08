package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.ArtistaDAO;
import com.setlisto.model.Artista;
import com.setlisto.model.DataException;
import com.setlisto.service.ArtistaService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class ArtistaServiceImpl implements ArtistaService {

	private static final Logger logger = LogManager.getLogger(ArtistaServiceImpl.class.getName());

	private ArtistaDAO artistaDAO = null;

	public ArtistaServiceImpl() {
		this.artistaDAO = new ArtistaDAO();
	}

	@Override
	public Artista findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Artista artista = artistaDAO.findById(c, id); // Lanza DataException
			commit = true;
			return artista;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar artista {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error inesperado en ArtistaService.findById: {}", e.getMessage());
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}


	@Override
	public List<Artista> findByMusicalEvent(Long eventId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Artista> artistas = artistaDAO.findByMusicalEventId(c, eventId);
			commit = true;
			return artistas;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar evento musical {}: {}", eventId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e){
			logger.error("Buscando por evento id {}: {}", eventId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}

	}

	@Override
	public Artista create(Artista artista) throws ServiceException {	
		Connection c = null; 												
		boolean commit = false; 											
		try {
			c = JDBCUtils.getConnection(); 												
			c.setAutoCommit(false); 										
			Artista created = artistaDAO.create(c, artista); 				
			commit = true; 													
			return created; 												
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar artista {}: {}", artista, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Creando {}: {}", artista, e.getMessage(), e); 	
			throw new ServiceException(e);												
		} finally {
			JDBCUtils.close(c, commit); 									
		}
	}

	@Override
	public List<Artista> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Artista> artistas = artistaDAO.findAll(c);			
			commit = true;
			return artistas;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todos los artista: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos los artistas", e.getMessage(), e);
			throw new ServiceException(e);		
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.ArtistaDAO;
import com.setlisto.model.Artista;
import com.setlisto.service.ArtistaService;
import com.setlisto.utils.JDBCUtils;

public class ArtistaServiceImpl implements ArtistaService {

	private static final Logger logger = LogManager.getLogger(ArtistaServiceImpl.class.getName());

	private ArtistaDAO artistaDAO = null;

	public ArtistaServiceImpl() {
		this.artistaDAO = new ArtistaDAO();
	}

	@Override
	public Artista findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Artista art = artistaDAO.findById(c, id);
			commit = true;
			return art;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<Artista> findByMusicalEvent(Long eventId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Artista> artistas = artistaDAO.findByMusicalEventId(c, eventId);
			commit = true;
			return artistas;
		} catch (Exception e){
			logger.error("Buscando por evento id {}: {}", eventId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
		
	}

	@Override
	public Artista create(Artista artista) throws Exception {	
		Connection c = null; 												// declara la conexion a null
		boolean commit = false; 											// declara la bandera por defecto false (no hace commit)
		try {
			c = JDBCUtils.getConnection(); 									// abre la conexion			
			c.setAutoCommit(false); 										// desactiva el autocommit para controlar la transaccion manualmente
			Artista created = artistaDAO.create(c, artista); 				// llamado normal al DAO
			commit = true; 													// Si todo va bien se levanta la bandera (Se hace commit)
			return created; 												// retorna en caso exitoso
		} catch (Exception e) {
			logger.error("Creando {}: {}", artista, e.getMessage(), e); 	// log del error
			throw e; 														// lanza excepcion hacia arriba en caso no exitoso
		} finally {
			JDBCUtils.close(c, commit); 									// se cierra la conexion y se hace o no el commid
		}
	}

	@Override
	public List<Artista> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Artista> artistas = artistaDAO.findAll(c);			
			commit = true;
			return artistas;
		} catch (Exception e) {
			logger.error("Buscando todos los artistas", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
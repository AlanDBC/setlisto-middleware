package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.dao.OrganizadorDAO;
import com.setlisto.model.Cliente;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;
import com.setlisto.service.EncryptionService;
import com.setlisto.service.MailService;
import com.setlisto.service.OrganizadorService;
import com.setlisto.utils.JDBCUtils;

public class OrganizadorServiceImpl implements OrganizadorService {

	private static final Logger logger = LogManager.getLogger(OrganizadorServiceImpl.class.getName());

	private OrganizadorDAO organizadorDAO = null;
	private EncryptionService encryptionService = null;
	private MailService mailService = null;

	public OrganizadorServiceImpl() {
		this.organizadorDAO = new OrganizadorDAO();
		this.encryptionService = new EncryptionServiceImpl();
		this.mailService = new MailServiceImpl();
	}

	@Override
	public Organizador register(Organizador organizador) throws Exception {
		// Cigrado y registro
		String passwordEncrypted = encryptionService.encrypt(organizador.getContrasena());
		organizador.setContrasena(passwordEncrypted);
		
		Connection c = null;
		boolean commit = false;
		Organizador organizadorRegistrado = null;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);

			Organizador existeOrganizador = organizadorDAO.findByEmail(c, organizador.getEmail());

			if (existeOrganizador != null) {
				throw new Exception("El usuario ya existe");
			}

			organizadorRegistrado = organizadorDAO.create(c, organizador);

			commit = true;
			return organizadorRegistrado;

		} catch (Exception e) {
			logger.error("Error registrando {}: {} ", organizador, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);

			if (organizadorRegistrado != null) {
				try {
					mailService.sendEmail(
							organizadorRegistrado.getEmail(), 
							"Bienvenido a Setlisto Partners",
							"Hola " + organizadorRegistrado.getNombre() + ", su cuenta de organizador ha sido registrada.");
				} catch (Exception e) {
					logger.warn("No se pudo enviar el email de bienvenida al organizador {}", organizadorRegistrado.getEmail());
				}
			} 
		}
	}

	@Override
	public Organizador login(String email, String password) throws Exception {
		Connection c = null;
		boolean commit = false; 
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Organizador organizador = organizadorDAO.findByEmail(c, email);

			if (organizador == null) {
				throw new Exception("Credenciales incorrectas");
			}

			boolean isPasswordCorrect = encryptionService.checkEncryption(password,organizador.getContrasena());
			if (!isPasswordCorrect) {
				throw new Exception("Credenciales incorrectas");
			}

			commit = true;
			return organizador;
		} catch (Exception e) {
			logger.error("Error en login para {}: {}", email, e.getMessage());
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}

	}

	@Override
	public Organizador findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Organizador organizador = organizadorDAO.findById(c, id);
			commit = true;
			return organizador;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<Organizador> findByCriteria(OrganizadorCriteria criteria, int from, int pageSize) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<Organizador> resultados = organizadorDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (Exception e) {
			logger.error("Buscando por criteria {}: {}", criteria, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean update(Organizador organizador) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean modificado = organizadorDAO.update(c, organizador);
			commit = true;
			return modificado;
		} catch (Exception e) {
			logger.error("Modificando organizador{}: {}", organizador, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Cambia el valor entre verificado/NoVerificado para organizadores
	 */
	@Override
	public boolean updateVerifiedStatus(Long id, boolean verificado) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean cambiado = organizadorDAO.updateVerifiedStatus(c, id, verificado);
			commit = true;
			return cambiado;
		} catch (Exception e) {
			logger.error("Modificando verificacion por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean delete(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean borrado = organizadorDAO.delete(c, id);
			commit = true;
			return borrado;
		} catch (Exception e) {
			logger.error("Borrando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<Organizador> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Organizador> organizadores = organizadorDAO.findAll(c);
			commit = true;
			return organizadores;
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}

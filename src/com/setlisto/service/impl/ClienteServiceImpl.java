package com.setlisto.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.dao.ClienteDAO;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;
import com.setlisto.service.ClienteService;
import com.setlisto.service.EncryptionService;
import com.setlisto.service.MailService;
import com.setlisto.utils.JDBCUtils;

public class ClienteServiceImpl implements ClienteService {

	private static final Logger logger = LogManager.getLogger(ClienteServiceImpl.class.getName());

	private EncryptionService encryptionService = null;
	private MailService mailService = null;
	private ClienteDAO clienteDAO = null;

	public ClienteServiceImpl() {
		encryptionService = new EncryptionServiceImpl();
		mailService = new MailServiceImpl();
		clienteDAO = new ClienteDAO();
	}

	@Override
	public Cliente register(Cliente cliente) throws Exception {
		Connection c = null;
		boolean commit = false;
		Cliente clienteRegistrado = null;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false); 

			Cliente existeCliente = clienteDAO.findByEmail(c, cliente.getEmail());

			if (existeCliente != null) {
				throw new Exception("El usuario ya existe");
			}

			// Cifrado y registro...
			String passwordEncrypted = encryptionService.encrypt(cliente.getContrasena());
			cliente.setContrasena(passwordEncrypted);

			clienteRegistrado = clienteDAO.create(c, cliente);

			commit = true;
			return clienteRegistrado;

		} catch (Exception e) {
			logger.error("Error registrando {}: {} ", cliente, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
			if (clienteRegistrado != null) {
				try {
					mailService.sendEmail(
							clienteRegistrado.getEmail(), 
							"Bienvenido a Setlisto!", 
							"Hola " + clienteRegistrado.getNombre() + ", bienvenido."
							);
				} catch (Exception e) {
					logger.warn("No se pudo enviar el email de bienvenida a {}", clienteRegistrado.getEmail());
				}

			}

		}
	}

	@Override
	public Cliente login(String correo, String contrasena) throws Exception {
	    Connection c = null;
	    boolean commit = false; 
	    try {
	        c = JDBCUtils.getConnection();
	        c.setAutoCommit(false);
	        Cliente cliente = clienteDAO.findByEmail(c, correo);
	        
	        if (cliente == null) {
	            throw new Exception("Credenciales incorrectas.");
	        }
	        
	        boolean isPasswordCorrect = encryptionService.checkEncryption(contrasena, cliente.getContrasena());
	        if (!isPasswordCorrect) {
	            throw new Exception("Credenciales incorrectas.");
	        }

	        commit = true; 
	        return cliente;

	    } catch (Exception e) {
	        logger.error("Error en login para {}: {}", correo, e.getMessage());
	        throw e;
	    } finally {
	        JDBCUtils.close(c, commit);
	    }
	}

	@Override
	public Cliente findByEmail(String email) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Cliente encontrado = clienteDAO.findByEmail(c, email);
			commit = true;

			return encontrado;
		} catch (Exception e) {
			logger.error("Error buscando cliente por email {}: {}", email, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Cliente findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Cliente encontrado = clienteDAO.findById(c, id);
			commit = true;
			return encontrado;
		} catch (Exception e) {
			logger.error("Error buscando cliente por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}				
	}

	@Override
	public boolean update(Cliente cliente) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean modificado = clienteDAO.update(c, cliente);
			commit = true;
			return modificado;
		} catch (Exception e){
			logger.error("Error modificando cliente {}: {}", cliente, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Cambia el valor entre activo/inactivo para clientes
	 */
	@Override 
	public boolean setActive(boolean active, Long customerId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();	
			c.setAutoCommit(false);
			boolean cambiado = clienteDAO.setActive(c ,active, customerId);
			commit = true;
			return cambiado;
		} catch (Exception e){
			logger.error("Error activando/desactivando cliente con id {}: {}", customerId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Cambia el valor entre verificado/sin verificado para clientes
	 */
	@Override 
	public boolean setVerify(boolean verified, Long customerId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean cambiado = clienteDAO.setVerify(c, verified, customerId);
			commit = true;
			return cambiado;
		} catch (Exception e){
			logger.error("Error cambiando valor verificado para cliente con id {}: {}", customerId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean updatePassword(Long id, String oldPassword, String newPassword) throws Exception {
	    Connection c = null;
	    boolean commit = false;
	    try {
	        c = JDBCUtils.getConnection();
	        c.setAutoCommit(false);

	        Cliente cnt = clienteDAO.findById(c, id);

	        if (cnt == null) {
	            throw new Exception("Usuario no encontrado.");
	        }
	        // Comprobar que la password vieja es correcta
	        if (!encryptionService.checkEncryption(oldPassword, cnt.getContrasena())) { 
	            // commit se queda en false.
	            return false; 
	        }
	        // Comprobar que la password nueva es distinta a la actual
	        if (encryptionService.checkEncryption(newPassword, cnt.getContrasena())) {
	            // Si intenta poner la misma, podemos dar error o simplemente retornar false
	            return false;
	        }
	        // Cifrar la nueva y actualizar el objeto
	        String encryptedNewPassword = encryptionService.encrypt(newPassword);
	        cnt.setContrasena(encryptedNewPassword);

	        boolean actualizado = clienteDAO.update(c, cnt);
	        if (actualizado) {
	            commit = true;
	        }
	        
	        return actualizado;
	    } catch (Exception e) {
	        logger.error("Error al actualizar contraseña para el ID {}: {}", id, e.getMessage());
	        throw e;
	    } finally {
	        // Cerramos y ejecutamos commit o rollback según la variable
	        JDBCUtils.close(c, commit);
	    }
	}

	@Override
	public void delete(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			clienteDAO.delete(c, id);
			commit = true;
			return;
		} catch (Exception e){
			logger.error("Error eliminando cliente con id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<Cliente> findByCriteria(ClienteCriteria criteria, int from, int pageSize) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<Cliente> resultados = clienteDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (Exception e){
			logger.error("Error buscando clientes con criteria {}: {}", criteria, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}	
	}
}

package com.setlisto.service.impl;

import java.sql.Connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.dao.ClienteDAO;
import com.setlisto.model.Cliente;
import com.setlisto.model.DataException;
import com.setlisto.model.Results;
import com.setlisto.service.ClienteService;
import com.setlisto.service.EncryptionService;
import com.setlisto.service.MailException;
import com.setlisto.service.MailService;
import com.setlisto.service.ServiceException;
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
	public Cliente register(Cliente cliente) throws ServiceException {
		// Encriptamos antes de tocar la BD para no "secuestrar" la conexión
		String passwordEncrypted = encryptionService.encrypt(cliente.getContrasena());
		cliente.setContrasena(passwordEncrypted);

		Connection c = null;
		boolean commit = false;
		Cliente clienteRegistrado = null;

		// 2. PERSISTENCIA (Transacción corta)
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false); 

			if (clienteDAO.findByEmail(c, cliente.getEmail()) != null) {
				throw new ServiceException("El usuario ya existe");
			}
			clienteRegistrado = clienteDAO.create(c, cliente);
			commit = true;
		} catch (DataException e) {
			logger.error("Error de persistencia al registrar cliente {}: {}", cliente, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error registrando {}: {} ", cliente.getEmail(), e.getMessage());
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}

		// Solo enviamos el email si la transacción terminó con éxito (commit == true)
		if (commit && clienteRegistrado != null) {
			enviarEmailBienvenida(clienteRegistrado);
		}
		return clienteRegistrado;
	}

	private void enviarEmailBienvenida(Cliente cliente) {
		try {
			mailService.sendEmail(
					cliente.getEmail(), 
					"Bienvenido a Setlisto!", 
					"Hola " + cliente.getNombre() + ", bienvenido."
					);
		} catch (MailException e) {
			// Logueamos pero no lanzamos excepción para no romper el registro
			// ya que el usuario YA se guardó en la BD.
			logger.warn("No se pudo enviar el email a {}", cliente.getEmail());
		}
	}

	@Override
	public Cliente login(String correo, String contrasena) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		Cliente cliente = null;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			cliente = clienteDAO.findByEmail(c, correo);

			if (cliente == null) {
				throw new ServiceException("Credenciales incorrectas.");
			}
			
			commit = true; 
		} catch (DataException e) {
			logger.error("Error de persistencia al logear cliente con correo {} y contrasena {}: {}", correo, contrasena, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error en login para {}: {}", correo, e.getMessage());
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}

		boolean isPasswordCorrect = encryptionService.checkEncryption(contrasena, cliente.getContrasena());
		if (!isPasswordCorrect) {
			throw new ServiceException("Credenciales incorrectas.");
		}
		return cliente;   
	}


	@Override
	public Cliente findByEmail(String email) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Cliente encontrado = clienteDAO.findByEmail(c, email);
			commit = true;

			return encontrado;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar cliente con correo {}: {}", email, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error buscando cliente por email {}: {}", email, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Cliente findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Cliente encontrado = clienteDAO.findById(c, id);
			commit = true;
			return encontrado;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar cliente con id{}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error buscando cliente por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}				
	}

	@Override
	public boolean update(Cliente cliente) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean modificado = clienteDAO.update(c, cliente);
			commit = true;
			return modificado;
		} catch (DataException e) {
			logger.error("Error de persistencia al modificar cliente {}: {}", cliente, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e){
			logger.error("Error modificando cliente {}: {}", cliente, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Cambia el valor entre activo/inactivo para clientes
	 */
	@Override 
	public boolean setActive(boolean active, Long customerId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();	
			c.setAutoCommit(false);
			boolean cambiado = clienteDAO.setActive(c ,active, customerId);
			commit = true;
			return cambiado;
		} catch (DataException e) {
			logger.error("Error de persistencia al cambiar valor de activo para cliente con id {}: {}", customerId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e){
			logger.error("Error activando/desactivando cliente con id {}: {}", customerId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	/**
	 * Cambia el valor entre verificado/sin verificado para clientes
	 */
	@Override 
	public boolean setVerify(boolean verified, Long customerId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean cambiado = clienteDAO.setVerify(c, verified, customerId);
			commit = true;
			return cambiado;
		} catch (DataException e) {
			logger.error("Error de persistencia al cambiar valor de verificado para cliente con id {}: {}", customerId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e){
			logger.error("Error cambiando valor verificado para cliente con id {}: {}", customerId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean updatePassword(Long id, String oldPassword, String newPassword) throws ServiceException {
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
		} catch (DataException e) {
			logger.error("Error de persistencia al cambiar contraseña cliente con id {}, contrasena vieja {}, contrasena nueva {}: {}",id ,oldPassword, newPassword, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Error al actualizar contraseña para el ID {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} finally {
			// Cerramos y ejecutamos commit o rollback según la variable
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
			clienteDAO.delete(c, id);
			commit = true;
			return;
		} catch (DataException e) {
			logger.error("Error de persistencia al borrar cliente con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e){
			logger.error("Error eliminando cliente con id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<Cliente> findByCriteria(ClienteCriteria criteria, int from, int pageSize) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<Cliente> resultados = clienteDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar con criteria {}: {}", criteria, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e){
			logger.error("Error buscando clientes con criteria {}: {}", criteria, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}	
	}
}

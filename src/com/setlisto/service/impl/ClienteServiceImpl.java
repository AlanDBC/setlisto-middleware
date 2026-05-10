package com.setlisto.service.impl;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.dao.ClienteDAO;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;
import com.setlisto.service.ClienteService;

public class ClienteServiceImpl implements ClienteService {

	private EncryptionServiceImpl encryptionService = null;
	private MailServiceImpl mailService = null;
	private ClienteDAO clienteDAO = null;

	public ClienteServiceImpl() {
		encryptionService = new EncryptionServiceImpl();
		mailService = new MailServiceImpl();
		clienteDAO = new ClienteDAO();
	}

	@Override
	public Cliente register(Cliente cliente) {
	    // 1. Comprobar si ya existe el usuario por email
	    Cliente existeCliente = findByEmail(cliente.getEmail());
	    if (existeCliente != null) {
	        // TODO: throw new ExcepcionUsuarioYaExiste
	        return null;
	    }

	    // 2. Cifrar la contraseña usando el servicio de cifrado 
	    String passwordEncrypted = encryptionService.encrypt(cliente.getContrasena());
	    cliente.setContrasena(passwordEncrypted);

	    // 3. Inserción en la base de datos
	    Cliente clienteRegistrado = clienteDAO.create(cliente);

	    // 4. Acciones post-registro (Envío de email)
	    if (clienteRegistrado != null) {
	        mailService.sendEmail(
	            clienteRegistrado.getEmail(), 
	            "Bienvenido a Setlisto!", 
	            "Hola " + clienteRegistrado.getNombre() + ", bienvenido a la plataforma."
	        );
	    }
	    return clienteRegistrado;
	}

	@Override
	public Cliente login(String correo, String contrasena) {
		// Buscar usuario por email
		Cliente cliente = clienteDAO.findByEmail(correo);
		if (cliente == null) {
			// No existe el usuario
			return null;
		}
		if (encryptionService.checkEncryption(contrasena,cliente.getContrasena())) {
			return cliente;			
		} else {
			return null;
		}	
	}

	@Override
	public Cliente findByEmail(String email) {
		return clienteDAO.findByEmail(email);
	}

	@Override
	public Cliente findById(Long id) {
		return clienteDAO.findById(id);
	}

	@Override
	public boolean update(Cliente cliente) {
		return clienteDAO.update(cliente);
	}

	@Override
	public boolean setActive(boolean active, Long customerId) {
		return clienteDAO.setActive(active, customerId);
	}

	@Override
	public boolean setVerify(boolean verified, Long customerId) {
		return clienteDAO.setVerify(verified, customerId);
	}

	@Override
	public boolean updatePassword(Long id, 
			String oldPassword, 
			String newPassword) {
		Cliente cnt = clienteDAO.findById(id);

		// Comprobar que la password vieja es correcta
		if (!encryptionService.checkEncryption(oldPassword, cnt.getContrasena())) {
			return false;
		}

		//  Comprobar que la password nueva y vieja son diferentes
		if (encryptionService.checkEncryption(newPassword, cnt.getContrasena())) {
			return false;
		}

		String encryptedNewPassword = encryptionService.encrypt(newPassword);
		cnt.setContrasena(encryptedNewPassword);
		
		return update(cnt);
	}

	@Override
	public void delete(Long id) {
		clienteDAO.delete(id);
	}
	
	@Override
	public Results<Cliente> findByCriteria(ClienteCriteria criteria, int from, int pageSize) {
		return clienteDAO.findByCriteria(criteria, from, pageSize);
	}	
}

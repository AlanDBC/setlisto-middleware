package com.setlisto.service;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;

public interface ClienteService {
	
	/**
	 * Registra un usuario dentro de la aplicacion
	 * @param correo
	 * @param contrasena
	 * @return id del cliente registrado
	 */
	public Cliente register (Cliente cliente);
	
	/**
	 * Logea un usuario dentro de la aplicacion 
	 * @param correo
	 * @param contrasena
	 * @return Cliente llogeado para pintar o null si no se ha podido logear
	 */
	public Cliente login (String correo, String contrasena);	
	
	/**
	 * Busca un cliente por su email
	 * @param email
	 * @return
	 */
	Cliente findByEmail(String email);
	
	/**
	 * Busca un cliente por su id
	 * @param id
	 * @return
	 */
	public Cliente findById(Long id);
	
	/**
	 * Modifica los datos de un usuario
	 * @param cliente
	 */
	public boolean update (Cliente cliente);

	public boolean setActive(boolean active, Long customerId);

	public boolean setVerify(boolean verified, Long customerId);
	
	/**
	 * Actualiza la contrase��a de un cliente
	 * @param id
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	boolean updatePassword(Long id, String oldPassword, String newPassword);
	
	/**
	 * Elimina un cliente de la aplicacion
	 * @param id
	 */
	public void delete (Long id);
	
	/**
	 * Busca clientes segun los criterios indicados
	 * @param criteria
	 * @return
	 */
	public Results<Cliente> findByCriteria(ClienteCriteria criteria, int from, int pageSize);
	
}

package com.setlisto.service;

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;

public interface ClienteService {
	
	/**
	 * Registra un cliente dentro de la aplicacion cifrando su contraseña y enviando email de bienvenida
	 * 
	 * @param cliente
	 * @return Cliente registrado
	 * @throws ServiceException
	 */
	public Cliente register (Cliente cliente) throws ServiceException;
	
	/**
	 * Autentica a un cliente comparando su contraseña con la encriptada en base de datos
	 *  
	 * @param correo
	 * @param contrasena
	 * @return Cliente logeado para pintar o null si no se ha podido logear
	 * @throws ServiceException
	 */
	public Cliente login (String correo, String contrasena) throws ServiceException;	
	
	/**
	 * Busca un cliente por su email
	 * 
	 * @param email
	 * @return Cliente encontrado
	 * @throws ServiceException
	 */
	Cliente findByEmail(String email) throws ServiceException;
	
	/**
	 * Busca un cliente por su id
	 * 
	 * @param id
	 * @return Cliente encontrado
	 * @throws ServiceException
	 */
	public Cliente findById(Long id) throws ServiceException;
	
	/**
	 * Modifica los datos de un Cliente
	 * 
	 * @param cliente
	 * @return true si se modifico, false en caso contrario
	 * @throws ServiceException
	 */
	public boolean update (Cliente cliente) throws ServiceException;

	/**
	 * Cambia el valor de "activo" para un cliente
	 * 
	 * @param activo (true para activar / false para desactivar)
	 * @param clienteId
	 * @return true si se cambio el valor, false en cado contrario
	 * @throws ServiceException
	 */
	public boolean setActive(boolean activo, Long clienteId) throws ServiceException;

	/**
	 * Cammbia el valor de "verificado" para un cliente
	 * 
	 * @param verificado (true para verificar / false para quitar verificado)
	 * @param clienteId
	 * @return true si se cambio el valor, false en caso contrario
	 * @throws ServiceException
	 */
	public boolean setVerify(boolean verificado, Long clienteId) throws ServiceException;
	
	/**
	 * Actualiza la contraseña de un cliente
	 * 
	 * @param id del cliente
	 * @param oldPassword contraseña antigua
	 * @param newPassword contraseña nueva
	 * @return true si se cambio la contraseña, false en caso contrario
	 * @throws ServiceException
	 */
	boolean updatePassword(Long id, String oldPassword, String newPassword) throws ServiceException;
	
	/**
	 * Borrado fisico de un cliente 
	 * 
	 * @param id del cliente
	 * @throws ServiceException
	 */
	public void delete (Long id) throws ServiceException;
	
	/**
	 * Busca clientes segun los criterios indicados
	 * 
	 * @param criteria
	 * @param from desde
	 * @param pageSize tamaño de la pagina
	 * @return Lista (Results<>) con los clientes que corresponten con los criterios
	 * @throws ServiceException
	 */
	public Results<Cliente> findByCriteria(ClienteCriteria criteria, int from, int pageSize) throws ServiceException;
	
}

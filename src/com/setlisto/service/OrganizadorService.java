package com.setlisto.service;

import java.util.List;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;

public interface OrganizadorService {

	/**
	 * Registra un organizador en la aplicacion cifrando su contraseña y enviando email de bienvenida
	 * 
	 * @param organizador a registrar
	 * @return Organizador registrado
	 * @throws ServiceException
	 */
	public Organizador register(Organizador organizador) throws ServiceException;


	/**
	 * Autentica a un organizador comparando su contraseña con la encriptada en base de datos
	 * 
	 * @param email
	 * @param password
	 * @return Organizador autenticado
	 * @throws ServiceException
	 */
	public Organizador login(String email, String password) throws ServiceException;

	/**
	 * Busca a un organizador por su id
	 * 
	 * @param id del organizador
	 * @return Prganizador encontrado
	 * @throws ServiceException
	 */
	public Organizador findById(Long id) throws ServiceException;

	/**
	 * Busca organizadores por criterios
	 * 
	 * @param criteria
	 * @param page desde
	 * @param size tamaño de pagina
	 * @return Lista (Result<>) de organizadores correspondientes con los criterios 
	 * @throws ServiceException
	 */
	public Results<Organizador> findByCriteria(OrganizadorCriteria criteria, int page, int size) throws ServiceException;

	/**
	 * Recupera todos los organizadores registrados en la aplicacion
	 * 
	 * @return Lista de organizadores
	 * @throws ServiceException
	 */
	public List<Organizador> findAll() throws ServiceException;

	/**
	 * Modifica los datos de un organizador
	 * 
	 * @param organizador a modificar
	 * @return true si se modifico, false en caso contrario
	 * @throws ServiceException
	 */
	public boolean update(Organizador organizador) throws ServiceException;

	/**
	 * Cambia el valor "verificado" de un organizador
	 * 
	 * @param id del organizador
	 * @param verificado (true para verificar / false para quitar verificacion)
	 * @return true si se modifico, false en caso contrario
	 * @throws ServiceException
	 */
	public boolean updateVerifiedStatus(Long id, boolean verificado) throws ServiceException;

	/**
	 * Borrado logico de un organizador
	 * 
	 * @param id del organizador
	 * @return true si se borro, false en caso contrario
	 * @throws ServiceException
	 */
	public boolean delete(Long id) throws ServiceException;
}

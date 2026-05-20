package com.setlisto.service;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.model.EventoMusical;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión integral de eventos musicales.
 */
public interface EventoMusicalService {

	/**
	 * Recupera la informacion completa de un evento musical, buscando por su id
	 * 
	 * @param id del evento
	 * @return Evento encontrado
	 * @throws ServiceException
	 */
	public EventoMusicalDTO findById(Long id) throws ServiceException;

	/**
	 * Permite buscar eventos musicales dinamicamente por criterios
	 * 
	 * @param criteria
	 * @param page desde
	 * @param size tamaño de pagina
	 * @return Lista de eventos (Result<>) correspondientes a los criterios
	 * @throws ServiceException
	 */
	public Results<EventoMusicalDTO> findByCriteria(EventoMusicalCriteria criteria, int page, int size) throws ServiceException;

	/**
	 * Crea un evento musical 
	 * 
	 * @param evento a crear
	 * @return EventoMusicalDTO para visualizacion a detalle
	 * @throws ServiceException
	 */
	public EventoMusicalDTO create(EventoMusicalDTO evento) throws ServiceException;

	/**
	 * Modifica los datos (no primordiales) de un evento musical
	 * 
	 * @param evento a modificar
	 * @throws ServiceException
	 */
	public void update(EventoMusicalDTO evento) throws ServiceException;

	/**
	 * Borrado fisico de un evento musical
	 * 
	 * @param id del evento a borrar
	 * @throws ServiceException
	 */
	public void delete(Long id) throws ServiceException;
}
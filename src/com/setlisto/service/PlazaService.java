package com.setlisto.service;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión y consulta de los asientos físicos de los recintos.
 */
public interface PlazaService {

    /**
     * Recupera una plaza y su informacion por su id
     * 
     * @param id
     * @return Plaza encontrada
     * @throws ServiceException
     */
    public PlazaDTO findById(Long id) throws ServiceException;

    /**
     * Busca una plaza por criterios
     * 
     * @param criteria
     * @param page desde
     * @param size tamaño de pagina 
     * @return Lista (Results<>) de plazas correspondientes con los criterios
     * @throws ServiceException
     */
    public Results<PlazaDTO> findByCriteria(PlazaCriteria criteria, int page, int size) throws ServiceException;
}
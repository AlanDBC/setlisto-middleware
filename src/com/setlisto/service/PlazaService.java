package com.setlisto.service;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión y consulta de los asientos físicos de los recintos.
 */
public interface PlazaService {

    /**
     * Recupera la información de una plaza específica por su ID.
     */
    public PlazaDTO findById(Long id) throws ServiceException;

    /**
     * Busca plazas aplicando filtros como el recinto (lugarId) o la categoría de asiento.
     */
    public Results<PlazaDTO> findByCriteria(PlazaCriteria criteria, int page, int size) throws ServiceException;
}
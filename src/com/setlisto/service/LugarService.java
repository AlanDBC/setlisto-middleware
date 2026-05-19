package com.setlisto.service;

import java.util.List;

import com.setlisto.criteria.LugarCriteria;
import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.model.Results;

public interface LugarService {

    /**
     * Recupera un lugar específico por su ID.
     */
    public LugarDTO findById(Long id) throws ServiceException;

    /**
     * Obtiene todos los lugares registrados.
     */
    public List<LugarDTO> findAll() throws ServiceException;

    /**
     * Realiza búsquedas de lugares mediante criterios geográficos (País, Región, Ciudad).
     */
    public Results<LugarDTO> findByCriteria(LugarCriteria criteria, int from, int pageSize) throws ServiceException;

    /**
     * Registra un nuevo recinto en el sistema.
     */
    public Lugar create(Lugar lugar) throws ServiceException;

    /**
     * Actualiza la información de un recinto existente.
     */
    public void update(Lugar lugar) throws ServiceException;

    /**
     * Elimina un recinto del sistema.
     */
    public void delete(Long id) throws ServiceException;
}
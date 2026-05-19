package com.setlisto.service;

import java.util.List;

import com.setlisto.model.SubGeneroMusicalDTO;

/**
 * Servicio para la gestión y consulta de los subgéneros musicales (ej. Nu Metal, Indie Pop).
 */
public interface SubGeneroMusicalService {

    /**
     * Recupera un subgénero específico por su ID.
     */
    public SubGeneroMusicalDTO findById(Long id) throws ServiceException;

    /**
     * Recupera todos los subgéneros asociados a un género padre (ej. todos los de 'Rock').
     */
    public List<SubGeneroMusicalDTO> findByGenero(Long generoId) throws ServiceException;
    
    /**
     * Recupera todos los subgéneros disponibles en el sistema.
     */
    public List<SubGeneroMusicalDTO> findAll() throws ServiceException;
}
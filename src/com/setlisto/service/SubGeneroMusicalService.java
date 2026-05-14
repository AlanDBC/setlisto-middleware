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
    public SubGeneroMusicalDTO findById(Long id) throws Exception;

    /**
     * Recupera todos los subgéneros asociados a un género padre (ej. todos los de 'Rock').
     */
    public List<SubGeneroMusicalDTO> findByGenero(Long generoId) throws Exception;
    
    /**
     * Recupera todos los subgéneros disponibles en el sistema.
     */
    public List<SubGeneroMusicalDTO> findAll() throws Exception;
}
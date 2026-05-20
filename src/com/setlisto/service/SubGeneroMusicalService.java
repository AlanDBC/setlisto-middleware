package com.setlisto.service;

import java.util.List;

import com.setlisto.model.SubGeneroMusicalDTO;

/**
 * Servicio para la gestión y consulta de los subgéneros musicales (ej. Nu Metal, Indie Pop).
 */
public interface SubGeneroMusicalService {

    /**
     * Recupera un subgenero musical por su id
     * 
     * @param id del subgenero
     * @return Subgenero encontrado
     * @throws ServiceException
     */
    public SubGeneroMusicalDTO findById(Long id) throws ServiceException;

    /**
     * Recupera la lista de subgeneros de un genero musical en concreto
     * 
     * @param generoId
     * @return Lista de subgeneros correspondientes
     * @throws ServiceException
     */
    public List<SubGeneroMusicalDTO> findByGenero(Long generoId) throws ServiceException;
    
    /**
     * Recupera todos los subgeneros musicales dentro de la aplicacion
     * 
     * @return
     * @throws ServiceException
     */
    public List<SubGeneroMusicalDTO> findAll() throws ServiceException;
}
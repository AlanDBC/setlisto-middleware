package com.setlisto.service;

import java.util.List;

import com.setlisto.model.GeneroMusical;

/**
 * Servicio para la gestión y consulta de géneros musicales maestros (Rock, Pop, Metal, etc.).
 */
public interface GeneroMusicalService {

    /**
     * Recupera un genero musical por su id
     * 
     * @param id del genero musical
     * @return Genero musical encontrado
     * @throws ServiceException
     */
    public GeneroMusical findById(Long id) throws ServiceException;

    /**
     * Trae todos los generos musicales dentro de la aplicacion
     * 
     * @return Lista de generos musicales
     * @throws ServiceException
     */
    public List<GeneroMusical> findAll() throws ServiceException;
}

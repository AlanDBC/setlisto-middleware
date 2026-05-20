package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Pais;

/**
 * Servicio para la gestión y consulta de los países disponibles en el sistema.
 */
public interface PaisService {

    /**
     * Recupera un país por su ID.
     * 
     * @param id del país.
     * @return Pais encontrado
     * @throws ServiceException
     */
    public Pais findById(Long id) throws ServiceException;

    /**
     * Obtiene el listado completo de países ordenados alfabéticamente.
     * 
     * @return Lista de objetos Pais.
     * @throws ServiceException
     */
    public List<Pais> findAll() throws ServiceException;
}
package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Pais;

/**
 * Servicio para la gestión y consulta de los países disponibles en el sistema.
 */
public interface PaisService {

    /**
     * Recupera un país por su ID.
     * @param id Identificador del país.
     * @return El objeto Pais o null si no se encuentra.
     */
    public Pais findById(Long id);

    /**
     * Obtiene el listado completo de países ordenados alfabéticamente.
     * @return Lista de objetos Pais.
     */
    public List<Pais> findAll();
}
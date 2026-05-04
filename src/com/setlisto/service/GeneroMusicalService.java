package com.setlisto.service;

import java.util.List;

import com.setlisto.model.GeneroMusical;

/**
 * Servicio para la gestión y consulta de géneros musicales maestros (Rock, Pop, Metal, etc.).
 */
public interface GeneroMusicalService {

    /**
     * Recupera un género musical por su ID.
     */
    public GeneroMusical findById(Long id);

    /**
     * Obtiene todos los géneros musicales disponibles ordenados por nombre.
     */
    public List<GeneroMusical> findAll();
}

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
    public LugarDTO findById(Long id);

    /**
     * Obtiene todos los lugares registrados.
     */
    public List<LugarDTO> findAll();

    /**
     * Realiza búsquedas de lugares mediante criterios geográficos (País, Región, Ciudad).
     */
    public Results<LugarDTO> findByCriteria(LugarCriteria criteria, int from, int pageSize);

    /**
     * Registra un nuevo recinto en el sistema.
     */
    public Lugar create(Lugar lugar);

    /**
     * Actualiza la información de un recinto existente.
     */
    public void update(Lugar lugar);

    /**
     * Elimina un recinto del sistema.
     */
    public void delete(Long id);
}
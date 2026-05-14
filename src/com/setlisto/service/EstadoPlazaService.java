package com.setlisto.service;

import java.util.List;

import com.setlisto.model.EstadoPlaza;

/**
 * Servicio para la gestión y consulta de los posibles estados de una plaza en un evento.
 */
public interface EstadoPlazaService {

    /**
     * Recupera un estado de plaza por su ID.
     * @param id Identificador del estado (1=AVAILABLE, 2=SOLD, 3=DISABLED).
     * @return El objeto EstadoPlaza o null si no existe.
     */
    public EstadoPlaza findById(Long id) throws Exception;

    /**
     * Obtiene el listado completo de estados de plaza disponibles.
     * @return Lista de objetos EstadoPlaza.
     */
    public List<EstadoPlaza> findAll() throws Exception;
}

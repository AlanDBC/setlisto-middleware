package com.setlisto.service;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.model.EventoMusical;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.Results;

/**
 * Servicio para la gestión integral de eventos musicales.
 */
public interface EventoMusicalService {

    /**
     * Recupera la información detallada de un evento por su ID.
     */
    public EventoMusicalDTO findById(Long id);

    /**
     * Realiza búsquedas dinámicas de eventos (por nombre, fechas, capacidad, géneros, etc.).
     */
    public Results<EventoMusicalDTO> findByCriteria(EventoMusicalCriteria criteria, int page, int size);

//    /**
//     * Obtiene el listado completo de eventos musicales.
//     */
//    public List<EventoMusicalDTO> findAll();

    /**
     * Registra un nuevo evento musical en el sistema.
     */
    public EventoMusicalDTO create(EventoMusicalDTO evento);

    /**
     * Actualiza los datos de un evento existente.
     */
    public void update(EventoMusicalDTO evento);

    /**
     * Elimina un evento del sistema.
     */
    public void delete(Long id);
}
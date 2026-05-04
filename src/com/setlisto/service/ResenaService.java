package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;

/**
 * Servicio para la gestión de valoraciones, comentarios y favoritos de eventos.
 */
public interface ResenaService {

    /**
     * Registra una nueva reseña en el sistema.
     * Según las reglas de negocio, un usuario solo puede tener una reseña por evento [5].
     */
    public Resena create(Resena resena);

    /**
     * Actualiza el contenido de una reseña existente (estrellas, comentario o favorito) [6].
     */
    public boolean update(Resena resena);

    /**
     * Elimina una reseña específica identificada por el evento y el usuario [7].
     */
    public boolean delete(Long eventoId, Long usuarioId);

    /**
     * Recupera una reseña concreta para comprobar si el usuario ya ha valorado el evento [8].
     */
    public ResenaDTO findById(Long eventoId, Long usuarioId);

    /**
     * Obtiene todas las reseñas de un evento musical específico [9].
     */
    public List<ResenaDTO> findByMusicalEvent(Long eventoId);

    /**
     * Obtiene el historial de reseñas publicadas por un cliente [10].
     */
    public List<ResenaDTO> findByCustomer(Long usuarioId);
}
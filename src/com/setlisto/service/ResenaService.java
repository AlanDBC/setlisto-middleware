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
     * Según las reglas de negocio, un usuario solo puede tener una reseña por evento
     */
    public Resena create(Resena resena) throws Exception;

    /**
     * Actualiza el contenido de una reseña existente (estrellas, comentario o favorito)
     */
    public boolean update(Resena resena) throws Exception;

    /**
     * Elimina una reseña específica identificada por el evento y el usuario
     */
    public boolean delete(Long eventoId, Long usuarioId) throws Exception;

    /**
     * Recupera una reseña concreta para comprobar si el usuario ya ha valorado el evento
     */
    public ResenaDTO findById(Long eventoId, Long usuarioId) throws Exception;

    /**
     * Obtiene todas las reseñas de un evento musical específico
     */
    public List<ResenaDTO> findByMusicalEvent(Long eventoId) throws Exception;

    /**
     * Obtiene el historial de reseñas publicadas por un cliente
     */
    public List<ResenaDTO> findByCustomer(Long usuarioId) throws Exception;
}
package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;

/**
 * Servicio para la gestión de valoraciones, comentarios y favoritos de eventos.
 */
public interface ResenaService {

    /**
     * Crea una reseña para un evento terminado
     * 
     * @param resena
     * @return Reseña creada
     * @throws ServiceException
     */
    public Resena create(Resena resena) throws ServiceException;

    /**
     * Actualiza el contenido de una reseña
     * 
     * @param resena
     * @return true si se actualizo, false en caso contrario
     * @throws ServiceException
     */
    public boolean update(Resena resena) throws ServiceException;

    /**
     * Elimina una reseña
     * 
     * @param eventoId
     * @param usuarioId
     * @return true si se borro, false en caso contrario
     * @throws ServiceException
     */
    public boolean delete(Long eventoId, Long usuarioId) throws ServiceException;

    /**
     * Recupera una reseña buscando por id de cliente e id de evento
     * 
     * @param eventoId
     * @param usuarioId
     * @return Reseña encontrada
     * @throws ServiceException
     */
    public ResenaDTO findById(Long eventoId, Long usuarioId) throws ServiceException;

    /**
     * Recupera las reseñas de un evento musical
     * 
     * @param eventoId
     * @return Lista de reseñas asociadas al evento
     * @throws ServiceException
     */
    public List<ResenaDTO> findByMusicalEvent(Long eventoId) throws ServiceException;

    /**
     * Recupera todas las reseñas hechas por un cliente
     * 
     * @param usuarioId
     * @return Lista de reseñas
     * @throws ServiceException
     */
    public List<ResenaDTO> findByCustomer(Long usuarioId) throws ServiceException;
}
package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Artista;

public interface ArtistaService {
    
    public Artista findById(Long id) throws Exception;
    
    /**
     * Obtiene la lista de artistas presentes en un evento musical
     * 
     * @param eventId
     * @return
     * @throws ServiceException
     */
    public List<Artista> findByMusicalEvent(Long eventId) throws ServiceException;
    
    /**
     * Crea un artista o grupo dentro de la aplicacion
     * 
     * @param Artista a crear
     * @return Artista creado
     * @throws ServiceException
     */
    public Artista create(Artista artista) throws ServiceException;
    
    /**
     * Retorna todos los artistas registrados en la aplicacion
     * 
     * @return Lista de artistas
     * @throws ServiceException
     */
    public List<Artista> findAll() throws ServiceException;
}
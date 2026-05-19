package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Artista;

public interface ArtistaService {
    
    public Artista findById(Long id) throws Exception;
    
    /**
     * Obtiene los artistas de un evento específico.
     */
    public List<Artista> findByMusicalEvent(Long eventId) throws ServiceException;
    
    public Artista create(Artista artista) throws ServiceException;
    
    public List<Artista> findAll() throws ServiceException;
}
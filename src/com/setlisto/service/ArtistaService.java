package com.setlisto.service;

import java.util.List;

import com.setlisto.model.Artista;

public interface ArtistaService {
    
    public Artista findById(Long id);
    
    /**
     * Obtiene los artistas de un evento específico.
     */
    public List<Artista> findByMusicalEvent(Long eventId);
    
    public Artista create(Artista artista);
    
    public List<Artista> findAll();
}
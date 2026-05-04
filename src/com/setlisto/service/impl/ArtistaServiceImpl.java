package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.ArtistaDAO;
import com.setlisto.model.Artista;
import com.setlisto.service.ArtistaService;

public class ArtistaServiceImpl implements ArtistaService {

    private ArtistaDAO artistaDAO = null;

    public ArtistaServiceImpl() {
        this.artistaDAO = new ArtistaDAO();
    }

    @Override
    public Artista findById(Long id) {
        return artistaDAO.findById(id);
    }

    @Override
    public List<Artista> findByMusicalEvent(Long eventId) {
        if (eventId == null) return null;
        return artistaDAO.findByMusicalEventId(eventId);
    }

    @Override
    public Artista create(Artista artista) {
        // Lógica de negocio: Validar que el nombre no sea nulo
        if (artista.getNombre() != null && !artista.getNombre().trim().isEmpty()) {
            return artistaDAO.create(artista);
        }
        return null;
    }

    @Override
    public List<Artista> findAll() {
        // En un caso real aquí llamaríamos a artistaDAO.getAll()
        return null; 
    }
}
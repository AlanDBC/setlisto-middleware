package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.SubGeneroMusicalDAO;
import com.setlisto.model.SubGeneroMusicalDTO;
import com.setlisto.service.SubGeneroMusicalService;

/**
 * Implementación del servicio de subgéneros musicales.
 */
public class SubGeneroMusicalServiceImpl implements SubGeneroMusicalService {

    private SubGeneroMusicalDAO subGeneroDAO = null;

    public SubGeneroMusicalServiceImpl() {
        this.subGeneroDAO = new SubGeneroMusicalDAO();
    }

    @Override
    public SubGeneroMusicalDTO findById(Long id) {
        // El DAO ya resuelve el nombre del género musical padre [1]
        return subGeneroDAO.findById(id);
    }

    @Override
    public List<SubGeneroMusicalDTO> findByGenero(Long generoId) {
        // Filtra los subgéneros según la tabla musical_subgenre [5]
        return subGeneroDAO.findByGeneroId(generoId);
    }
}
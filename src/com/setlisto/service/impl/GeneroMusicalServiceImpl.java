package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.GeneroMusicalDAO;
import com.setlisto.model.GeneroMusical;
import com.setlisto.service.GeneroMusicalService;

public class GeneroMusicalServiceImpl implements GeneroMusicalService {

    private GeneroMusicalDAO generoMusicalDAO = null;

    public GeneroMusicalServiceImpl() {
        this.generoMusicalDAO = new GeneroMusicalDAO();
    }

    @Override
    public GeneroMusical findById(Long id) {
        return generoMusicalDAO.findById(id);
    }

    @Override
    public List<GeneroMusical> findAll() {
        return generoMusicalDAO.findAll();
    }
}
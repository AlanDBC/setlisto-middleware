package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.PaisDAO;
import com.setlisto.model.Pais;
import com.setlisto.service.PaisService;

/**
 * Implementación del servicio de países.
 */
public class PaisServiceImpl implements PaisService {

    private PaisDAO paisDAO = null;

    public PaisServiceImpl() {
        this.paisDAO = new PaisDAO();
    }

    @Override
    public Pais findById(Long id) {
        // El DAO ya maneja la conexión y el mapeo a través de JDBCUtils
        return paisDAO.findById(id);
    }

    @Override
    public List<Pais> findAll() {
        // El DAO devuelve la lista ordenada por nombre según la consulta SQL
        return paisDAO.findAll();
    }
}
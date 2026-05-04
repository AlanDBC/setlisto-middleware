package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.SubTipoEventoDAO;
import com.setlisto.model.SubTipoEventoDTO;
import com.setlisto.service.SubTipoEventoService;

/**
 * Implementación del servicio de subtipos de eventos.
 */
public class SubTipoEventoServiceImpl implements SubTipoEventoService {

    private SubTipoEventoDAO subTipoEventoDAO = null;

    public SubTipoEventoServiceImpl() {
        this.subTipoEventoDAO = new SubTipoEventoDAO();
    }

    @Override
    public SubTipoEventoDTO findById(Long id) {
        // El DAO ya realiza el JOIN con event_type para obtener el nombre del tipo padre
        return subTipoEventoDAO.findById(id);
    }

    @Override
    public List<SubTipoEventoDTO> findByTipoEvento(Long tipoEventoId) {
        // Utiliza el método del DAO para filtrar por el ID del tipo principal
        return subTipoEventoDAO.findByTipoEventoId(tipoEventoId);
    }
}
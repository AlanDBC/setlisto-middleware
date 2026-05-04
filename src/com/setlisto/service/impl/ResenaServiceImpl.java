package com.setlisto.service.impl;

import java.util.List;

import com.setlisto.dao.ResenaDAO;
import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;
import com.setlisto.service.ResenaService;

public class ResenaServiceImpl implements ResenaService {

    private ResenaDAO resenaDAO = null;

    public ResenaServiceImpl() {
        this.resenaDAO = new ResenaDAO();
    }

    @Override
    public Resena create(Resena resena) {
        // 1. Validación de Regla de Negocio: Evitar duplicados (PK compuesta en BD)
        ResenaDTO existente = resenaDAO.findByEventAndCustomer(resena.getEventoId(), resena.getClienteId());
        
        if (existente != null) {
            // Si ya existe, no creamos una nueva. El controlador debería sugerir "Editar"
            return null;
        }

        // 2. Persistencia
        boolean exito = resenaDAO.create(resena);
        return exito ? resena : null;
    }

    @Override
    public boolean update(Resena resena) {
        // Permite modificar estrellas, comentario o el flag de favorito 
        return resenaDAO.edit(resena);
    }

    @Override
    public boolean delete(Long eventoId, Long usuarioId) {
        return resenaDAO.delete(eventoId, usuarioId);
    }

    @Override
    public ResenaDTO findById(Long eventoId, Long usuarioId) {
        return resenaDAO.findByEventAndCustomer(eventoId, usuarioId);
    }

    @Override
    public List<ResenaDTO> findByMusicalEvent(Long eventoId) {
        return resenaDAO.findByMusicalEvent(eventoId);
    }

    @Override
    public List<ResenaDTO> findByCustomer(Long usuarioId) {
        return resenaDAO.findByCustomer(usuarioId);
    }
}
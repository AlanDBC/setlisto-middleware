package com.setlisto.service.impl;

import com.setlisto.criteria.PlazaEnEventoCriteria;
import com.setlisto.dao.PlazaEnEventoDAO;
import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.model.Results;
import com.setlisto.service.PlazaEnEventoService;

public class PlazaEnEventoServiceImpl implements PlazaEnEventoService {

    private PlazaEnEventoDAO plazaEnEventoDAO = null;

    public PlazaEnEventoServiceImpl() {
        this.plazaEnEventoDAO = new PlazaEnEventoDAO();
    }

    @Override
    public PlazaEnEventoDTO findById(Long id) {
        return plazaEnEventoDAO.findById(id);
    }

    @Override
    public Results<PlazaEnEventoDTO> findByCriteria(PlazaEnEventoCriteria criteria, int from, int pageSize) {
        if (criteria == null) {
            criteria = new PlazaEnEventoCriteria();
        }
        return plazaEnEventoDAO.findByCriteria(criteria, from, pageSize);
    }

    @Override
    public boolean isSeatAvailable(Long plazaEnEventoId) {
        // Según el DAO, comprueba si el estado es ID 1 (AVAILABLE)
        return plazaEnEventoDAO.isAvailable(plazaEnEventoId);
    }

    @Override
    public boolean updateStatus(Long plazaEnEventoId, Long statusId) {
        // Cambia el estado en la tabla seat_of_musical_event
        if (plazaEnEventoId != null && statusId != null) {
            return plazaEnEventoDAO.updateStatus(plazaEnEventoId, statusId);
        }
        return false;
    }

    @Override
    public int getAvailableCount(Long eventId) {
        // Retorna el conteo de plazas con estado 'AVAILABLE' para un evento
        return plazaEnEventoDAO.countAvailableByEvent(eventId);
    }
}
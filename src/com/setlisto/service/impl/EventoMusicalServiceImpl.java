package com.setlisto.service.impl;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.dao.EventoMusicalDAO;
import com.setlisto.model.EventoMusical;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.Results;
import com.setlisto.service.EventoMusicalService;

public class EventoMusicalServiceImpl implements EventoMusicalService {

    private EventoMusicalDAO eventoMusicalDAO = null;

    public EventoMusicalServiceImpl() {
        this.eventoMusicalDAO = new EventoMusicalDAO();
    }

    @Override
    public EventoMusicalDTO findById(Long id) {
        return eventoMusicalDAO.findById(id);
    }

    @Override
    public Results<EventoMusicalDTO> findByCriteria(EventoMusicalCriteria criteria, int from, int pageSize) {
        return eventoMusicalDAO.findByCriteria(criteria, from, pageSize);
    }

    @Override
    public EventoMusicalDTO create(EventoMusicalDTO evento) {
            return eventoMusicalDAO.create(evento);
    }

    @Override
    public void update(EventoMusicalDTO evento) {
        eventoMusicalDAO.update(evento);
    }

    @Override
    public void delete(Long id) {
        eventoMusicalDAO.delete(id);
    }
}
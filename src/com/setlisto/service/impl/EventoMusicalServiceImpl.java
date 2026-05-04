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

//    @Override
//    public List<EventoMusicalDTO> findAll() {
//        // Al pasar un criteria vacío, el DAO devuelve todos los registros [1]
//        return eventoMusicalDAO.findByCriteria(new EventoMusicalCriteria(), 0, Integer.MAX_VALUE);
//    }

    @Override
    public EventoMusical create(EventoMusical evento) {
        // Validación: Un evento debe tener una capacidad positiva y fechas coherentes
        if (evento.getCapacidad() != null && evento.getCapacidad() > 0) {
            return eventoMusicalDAO.create(evento);
        }
        return null;
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
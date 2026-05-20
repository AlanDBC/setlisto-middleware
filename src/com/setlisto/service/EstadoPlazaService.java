package com.setlisto.service;

import java.util.List;

import com.setlisto.model.EstadoPlaza;

/**
 * Servicio para la gestión y consulta de los posibles estados de una plaza en un evento.
 */
public interface EstadoPlazaService {

    /**
     * Recupera un estado de plaza por su ID.
     * 
     * @param id del estado (1=Disponible, 2=Vendido, 3=Deshabilitado).
     * @return Estado encontrado
     * @throws ServiceException
     */
    public EstadoPlaza findById(Long id) throws ServiceException;

    /**
     * Obtiene el listado completo de estados de plaza disponibles.
     * 
     * @return Lista estados
     * @throws ServiceException
     */
    public List<EstadoPlaza> findAll() throws ServiceException;
}

package com.setlisto.service;

import java.util.List;

import com.setlisto.criteria.LugarCriteria;
import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.model.Results;

public interface LugarService {

    /**
     * Recupera un lugar por su id
     * 
     * @param id del lugar
     * @return Lugar encontrado
     * @throws ServiceException
     */
    public LugarDTO findById(Long id) throws ServiceException;

    /**
     * Encuentra todos los lugares registrados en la aplicacion
     * 
     * @return Lista de lugares
     * @throws ServiceException
     */
    public List<LugarDTO> findAll() throws ServiceException;

    /**
     * Permite buscar lugares por criterios especificos
     * 
     * @param criteria
     * @param from desde
     * @param pageSize tamaño de pagina
     * @return Lista (Results<>) con los lugares correspondientes a los criterios
     * @throws ServiceException
     */
    public Results<LugarDTO> findByCriteria(LugarCriteria criteria, int from, int pageSize) throws ServiceException;

    /**
     * Crea un lugar (recinto) para un evento musical
     * 
     * @param lugar a crear
     * @return Lugar creado
     * @throws ServiceException
     */
    public Lugar create(Lugar lugar) throws ServiceException;

    /**
     * Actualiza la informacion referente a un lugar
     * 
     * @param lugar a modificar
     * @throws ServiceException
     */
    public void update(Lugar lugar) throws ServiceException;

    /**
     * Elimina un lugar (recinto) del sistema
     * 
     * @param id del recinto a borrar
     * @throws ServiceException
     */
    public void delete(Long id) throws ServiceException;
}
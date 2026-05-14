package com.setlisto.service;

import java.util.List;

import com.setlisto.model.MonedaPago;

/**
 * Servicio para la gestión y consulta de las monedas aceptadas en los pagos.
 */
public interface MonedaPagoService {

    /**
     * Recupera una moneda específica por su ID.
     * @param id Identificador de la moneda (ej. 1 para EUR).
     * @return El objeto MonedaPago o null si no existe.
     */
    public MonedaPago findById(Long id) throws Exception;

    /**
     * Obtiene el listado completo de monedas registradas en el sistema.
     * @return Lista de objetos MonedaPago.
     */
    public List<MonedaPago> findAll() throws Exception;
}
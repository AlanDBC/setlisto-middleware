package com.setlisto.service;

import java.util.List;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;

public interface OrganizadorService {
    /** Registra un organizador cifrando su contraseña. */
    public Organizador register(Organizador organizador) throws Exception;

    /** Autentica a un organizador comparando hashes. */
    public Organizador login(String email, String password) throws Exception;

    /** Busca organizadores por su id. */
    public Organizador findById(Long id) throws Exception;

    /** Permite búsquedas por nombre comercial, email o estado de verificación. */
    public Results<Organizador> findByCriteria(OrganizadorCriteria criteria, int page, int size) throws Exception;
    
    public List<Organizador> findAll() throws Exception;

    public boolean update(Organizador organizador) throws Exception;

    /** Cambia el estado de verificación (fundamental para CU11: Gestión de Usuarios). */
    public boolean updateVerifiedStatus(Long id, boolean verificado) throws Exception;
    
    /** Borrado logico de organizadores. */
    public boolean delete(Long id) throws Exception;
}

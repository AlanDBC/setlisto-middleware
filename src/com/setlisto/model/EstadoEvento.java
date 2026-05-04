package com.setlisto.model;

/**
 * Representa los estados maestros de un evento musical (BORRADOR, PROGRAMADO, etc.).
 */
public class EstadoEvento extends AbstractValueObject {
    private Long id;
    private String nombre;

    public EstadoEvento() {
    }
    
    public EstadoEvento (Long id, String nombre) {
    	this.id = id;
    	this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
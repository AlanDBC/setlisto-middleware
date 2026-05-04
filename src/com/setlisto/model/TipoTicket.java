package com.setlisto.model;

public class TipoTicket extends AbstractValueObject {

    private Long id;
    private String nombre;

    public TipoTicket() {
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
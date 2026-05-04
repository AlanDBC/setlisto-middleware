package com.setlisto.model;

public class EstadoPago extends AbstractValueObject {
	private Long id;
	private String nombre;
	
	public EstadoPago() {
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

	public void setNombre(String estado) {
		this.nombre = estado;
	}
}

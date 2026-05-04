package com.setlisto.model;

public class MetodoPago extends AbstractValueObject {
	private Long id;
	private String nombre;

	public MetodoPago() {
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}

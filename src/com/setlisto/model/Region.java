package com.setlisto.model;

public class Region extends AbstractValueObject {
	
	private Long id;
	private String nombre;
	private Long paisId;
	
	public Region() {
	}
	
	public Region(Long id, String nombre) {
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

	public Long getPaisId() {
		return paisId;
	}

	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}
	

}

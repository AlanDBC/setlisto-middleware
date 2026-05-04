package com.setlisto.model;

public class SubGeneroMusical {
	private Long id;
	private String nombre;
	private Long generoMusicalId;
	
	public SubGeneroMusical() {
	}
	
	public SubGeneroMusical(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Long getGeneroMusicalId() {
		return generoMusicalId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setGeneroMusicalId(Long generoMusicalId) {
		this.generoMusicalId = generoMusicalId;
	}

}

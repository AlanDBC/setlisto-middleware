package com.setlisto.model;

public class SubGeneroMusicalDTO extends SubGeneroMusical {
	private String generoMusicalNombre;
	
	public SubGeneroMusicalDTO() {
		super();
	}
	
	public SubGeneroMusicalDTO (Long id, String nombre) {
		super(id, nombre);
	}

	public String getGeneroMusicalNombre() {
		return generoMusicalNombre;
	}

	public void setGeneroMusicalNombre(String generoMusicalNombre) {
		this.generoMusicalNombre = generoMusicalNombre;
	}
}

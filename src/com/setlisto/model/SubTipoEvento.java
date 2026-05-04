package com.setlisto.model;

public class SubTipoEvento {
	private Long id;
	private String nombre;
	private Long tipoEventoId;
	
	public SubTipoEvento() {
	}
	
	public SubTipoEvento(Long id,String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Long getTipoEventoId() {
		return tipoEventoId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setTipoEventoId(Long tipoEventoId) {
		this.tipoEventoId = tipoEventoId;
	}
}

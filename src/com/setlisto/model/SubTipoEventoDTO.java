package com.setlisto.model;

public class SubTipoEventoDTO extends SubTipoEvento {
	private String tipoEventoNombre;
	
	public SubTipoEventoDTO() {
		super();
	}
	
	public SubTipoEventoDTO(Long id, String nombre) {
		super(id, nombre);
	}
	

	public String getTipoEventoNombre() {
		return tipoEventoNombre;
	}

	public void setTipoEventoNombre(String tipoEventoNombre) {
		this.tipoEventoNombre = tipoEventoNombre;
	}
}

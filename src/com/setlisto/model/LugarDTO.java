package com.setlisto.model;

public class LugarDTO extends Lugar {
	private String ciudadNombre;
	private String zonaHorariaNombre;
	
	public LugarDTO() {
	}
	
	public String getCiudadNombre() {
		return ciudadNombre;
	}

	public void setCiudadNombre(String ciudadNombre) {
		this.ciudadNombre = ciudadNombre;
	}

	public String getZonaHorariaNombre() {
		return zonaHorariaNombre;
	}

	public void setZonaHorariaNombre(String zonaHorariaNombre) {
		this.zonaHorariaNombre = zonaHorariaNombre;
	}
}

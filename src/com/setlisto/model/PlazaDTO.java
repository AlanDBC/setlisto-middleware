package com.setlisto.model;

public class PlazaDTO extends Plaza {	
	private String categoriaNombre;
	private String lugarNombre;

	public PlazaDTO() {	
	}

	public String getCategoriaNombre() {
		return categoriaNombre;
	}

	public void setCategoriaNombre(String categoriaNombre) {
		this.categoriaNombre = categoriaNombre;
	}

	public String getLugarNombre() {
		return lugarNombre;
	}

	public void setLugarNombre(String lugarNombre) {
		this.lugarNombre = lugarNombre;
	}
}

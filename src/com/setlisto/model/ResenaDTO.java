package com.setlisto.model;

public class ResenaDTO extends Resena {	
	
	private String nombreEvento;
	private String nombreUsuario;
	
	public ResenaDTO() {
	}

	public String getNombreEvento() {
		return nombreEvento;
	}

	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
}

package com.setlisto.model;

public class Lugar extends AbstractValueObject {
	
	private Long id;
	private String nombre;
	private String direccion;
	private Long ciudadId;
	private Long idZonaHoraria;

	public Lugar() {
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

	public Long getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(Long ciudadId) {
		this.ciudadId = ciudadId;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Long getIdZonaHoraria() {
		return idZonaHoraria;
	}

	public void setIdZonaHoraria(Long idZonaHoraria) {
		this.idZonaHoraria = idZonaHoraria;
	}
}

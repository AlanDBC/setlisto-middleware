package com.setlisto.model;

import java.util.List;

/**
 * Data Transfer Object (DTO) para representar un evento musical con información adicional.
 * Este DTO extiende la clase EventoMusical y agrega campos para mostrar información relacionada
 * como el nombre del organizador, lugar, ciudad, tipo de evento, subtipo y estado.
 */
public class EventoMusicalDTO extends EventoMusical {

	// idOrganizador esta en EventoMusical
	private String organizadorNombre = null;

	// idLugar esta en EventoMusical
	private String lugarNombre = null;
	private String lugarDireccion = null;

	private Long idCiudad = null;
	private String ciudadNombre = null;

	private Long idTipo = null;
	private String tipoNombre = null;
	
	// idSubtipo esta en EventoMusical. por lo general solo habra un subtipo, en UI mostraremos dos comboBox, uno para el tipo y otro para el subtipo 
	private String subtipoNombre = null;

	// idEstado esta en EventoMusical
	private String estadoNombre = null;
	
	// idZonaHoraria esta en EventoMusical
	private String zonaHorariaNombre = null;

	private Integer entradasRestantes = null;
	
	private List<GeneroMusical> generos = null; // en caso de un tipo de evento con varios géneros musicales, se almacenarán aquí. Si solo hay uno, se almacenará solo uno
	private List<SubGeneroMusical> subGeneros = null;  

	private List<Artista> artistas = null;
	// guardamos en listas para poder poner en UI, JScrollPane + JList y mostrar varios géneros y subgéneros
	

	public EventoMusicalDTO() {
	}

	public String getOrganizadorNombre() {
		return organizadorNombre;
	}

	public void setOrganizadorNombre(String organizadorNombre) {
		this.organizadorNombre = organizadorNombre;
	}

	public String getLugarNombre() {
		return lugarNombre;
	}

	public void setLugarNombre(String lugarNombre) {
		this.lugarNombre = lugarNombre;
	}

	public String getLugarDireccion() {
		return lugarDireccion;
	}

	public void setLugarDireccion(String lugarDireccion) {
		this.lugarDireccion = lugarDireccion;
	}

	public Long getIdCiudad() {
		return idCiudad;
	}

	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}

	public String getCiudadNombre() {
		return ciudadNombre;
	}

	public void setCiudadNombre(String ciudadNombre) {
		this.ciudadNombre = ciudadNombre;
	}

	public Long getIdTipo() {
		return idTipo;
	}

	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}

	public String getTipoNombre() {
		return tipoNombre;
	}

	public void setTipoNombre(String tipoNombre) {
		this.tipoNombre = tipoNombre;
	}

	public String getSubtipoNombre() {
		return subtipoNombre;
	}

	public void setSubtipoNombre(String subtipoNombre) {
		this.subtipoNombre = subtipoNombre;
	}

	public String getEstadoNombre() {
		return estadoNombre;
	}

	public void setEstadoNombre(String estadoNombre) {
		this.estadoNombre = estadoNombre;
	}

	public String getZonaHorariaNombre() {
		return zonaHorariaNombre;
	}

	public void setZonaHorariaNombre(String zonaHorariaNombre) {
		this.zonaHorariaNombre = zonaHorariaNombre;
	}

	public Integer getEntradasRestantes() {
		return entradasRestantes;
	}

	public List<GeneroMusical> getGeneros() {
		return generos;
	}

	public List<SubGeneroMusical> getSubGeneros() {
		return subGeneros;
	}

	public List<Artista> getArtistas() {
		return artistas;
	}

	public void setEntradasRestantes(Integer entradasRestantes) {
		this.entradasRestantes = entradasRestantes;
	}

	public void setGeneros(List<GeneroMusical> generos) {
		this.generos = generos;
	}

	public void setSubGeneros(List<SubGeneroMusical> subGeneros) {
		this.subGeneros = subGeneros;
	}

	public void setArtistas(List<Artista> artistas) {
		this.artistas = artistas;
	}
}

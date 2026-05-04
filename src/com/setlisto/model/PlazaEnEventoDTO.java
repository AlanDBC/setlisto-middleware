package com.setlisto.model;

public class PlazaEnEventoDTO extends AbstractValueObject {
	private Long id;
	private Long eventoMusicalId;
	private String eventoMusicalNombre;
	private Long plazaId;
	private String plazaFila;
	private Long plazaNumero;
	private Long estadoPlazaId;
	private String estadoPlazaNombre;
	
	public PlazaEnEventoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventoMusicalId() {
		return eventoMusicalId;
	}

	public void setEventoMusicalId(Long eventoMusicalId) {
		this.eventoMusicalId = eventoMusicalId;
	}

	public Long getPlazaId() {
		return plazaId;
	}

	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}

	public Long getEstadoPlazaId() {
		return estadoPlazaId;
	}

	public void setEstadoPlazaId(Long estadoPlazaId) {
		this.estadoPlazaId = estadoPlazaId;
	}

	public String getEventoMusicalNombre() {
		return eventoMusicalNombre;
	}

	public String getEstadoPlazaNombre() {
		return estadoPlazaNombre;
	}

	public void setEventoMusicalNombre(String eventoMusicalNombre) {
		this.eventoMusicalNombre = eventoMusicalNombre;
	}

	public void setEstadoPlazaNombre(String estadoPlazaNombre) {
		this.estadoPlazaNombre = estadoPlazaNombre;
	}

	public String getPlazaFila() {
		return plazaFila;
	}

	public Long getPlazaNumero() {
		return plazaNumero;
	}

	public void setPlazaFila(String plazaFila) {
		this.plazaFila = plazaFila;
	}

	public void setPlazaNumero(Long plazaNumero) {
		this.plazaNumero = plazaNumero;
	}	
}

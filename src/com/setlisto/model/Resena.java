package com.setlisto.model;

import java.time.LocalDateTime;

public class Resena extends AbstractValueObject {

	private Long eventoId;
	private Long clienteId;
	private Double estrellas;
	private Boolean favorito;
	private String comentario;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	
	public Resena() {
	}

	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Double getEstrellas() {
		return estrellas;
	}

	public void setEstrellas(Double estrellas) {
		this.estrellas = estrellas;
	}

	public Boolean getFavorito() {
		return favorito;
	}

	public void setFavorito(Boolean favorito) {
		this.favorito = favorito;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
}

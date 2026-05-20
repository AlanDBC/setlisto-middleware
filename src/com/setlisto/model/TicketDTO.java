package com.setlisto.model;

import java.time.LocalDateTime;

public class TicketDTO extends Ticket {
	// Quien compra el ticket
	private Long clienteId;
	private String clienteNombre;
	// Evento al que pertenece el ticket e informacion
	private Long eventoId;
	private String eventoNombre;
	private LocalDateTime eventoFecha;
	private Long lugarId;
	private String lugarNombre;
	private String lugarDireccion;
	// Plaza unica asociada al ticket 
	private String plazaFila;
	private Long plazaNumero;
	private Long plazaCategoriaId;
	private String plazaCategoriaNombre;
	private String zonaEventoNombre; 
	private Integer zonaEventoCapacidadDisponible; 
	// Tipo de ticket
	private String tipoTicketNombre;
	
	public TicketDTO() {
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public Long getEventoId() {
		return eventoId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	public String getEventoNombre() {
		return eventoNombre;
	}

	public void setEventoNombre(String eventoNombre) {
		this.eventoNombre = eventoNombre;
	}

	public LocalDateTime getEventoFecha() {
		return eventoFecha;
	}

	public void setEventoFecha(LocalDateTime eventoFecha) {
		this.eventoFecha = eventoFecha;
	}

	public Long getLugarId() {
		return lugarId;
	}

	public void setLugarId(Long lugarId) {
		this.lugarId = lugarId;
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

	public String getPlazaFila() {
		return plazaFila;
	}

	public void setPlazaFila(String plazaFila) {
		this.plazaFila = plazaFila;
	}

	public Long getPlazaNumero() {
		return plazaNumero;
	}

	public void setPlazaNumero(Long plazaNumero) {
		this.plazaNumero = plazaNumero;
	}

	public Long getPlazaCategoriaId() {
		return plazaCategoriaId;
	}

	public void setPlazaCategoriaId(Long plazaCategoriaId) {
		this.plazaCategoriaId = plazaCategoriaId;
	}

	public String getPlazaCategoriaNombre() {
		return plazaCategoriaNombre;
	}

	public void setPlazaCategoriaNombre(String plazaCategoriaNombre) {
		this.plazaCategoriaNombre = plazaCategoriaNombre;
	}

	public String getTipoTicketNombre() {
		return tipoTicketNombre;
	}

	public void setTipoTicketNombre(String tipoTicketNombre) {
		this.tipoTicketNombre = tipoTicketNombre;
	}

	public String getZonaEventoNombre() {
		return zonaEventoNombre;
	}

	public Integer getZonaEventoCapacidadDisponible() {
		return zonaEventoCapacidadDisponible;
	}

	public void setZonaEventoNombre(String zonaEventoNombre) {
		this.zonaEventoNombre = zonaEventoNombre;
	}

	public void setZonaEventoCapacidadDisponible(Integer zonaEventoCapacidadDisponible) {
		this.zonaEventoCapacidadDisponible = zonaEventoCapacidadDisponible;
	}
}

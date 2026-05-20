package com.setlisto.model;

import java.time.LocalDateTime;

/**
 * POJO para la entidad EventoMusical.
 */
public class EventoMusical extends AbstractValueObject {	
	private Long id = null;
	private String nombre = null;
	private String descripcion = null;
	private LocalDateTime fechaInicio = null;
	private LocalDateTime fechaFin = null;	
	private Long idOrganizador = null;	
	private Long idLugar = null;	
	private Integer capacidad = null;
	private Long idSubtipo = null;
	private Long idEstado = null;
	private Long idZonaHoraria = null;
	private String rutaImagenPlano = null;
	 
	public EventoMusical() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getIdOrganizador() {
		return idOrganizador;
	}

	public void setIdOrganizador(Long idOrganizador) {
		this.idOrganizador = idOrganizador;
	}

	public Long getIdLugar() {
		return idLugar;
	}

	public void setIdLugar(Long idLugar) {
		this.idLugar = idLugar;
	}

	public Integer getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(Integer capacidad) {
		this.capacidad = capacidad;
	}

	public Long getIdSubtipo() {
		return idSubtipo;
	}

	public void setIdSubtipo(Long idSubtipo) {
		this.idSubtipo = idSubtipo;
	}

	public Long getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(Long idEstado) {
		this.idEstado = idEstado;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getIdZonaHoraria() {
		return idZonaHoraria;
	}

	public void setIdZonaHoraria(Long idZonaHoraria) {
		this.idZonaHoraria = idZonaHoraria;
	}

	public String getRutaImagenPlano() {
		return rutaImagenPlano;
	}

	public void setRutaImagenPlano(String rutaImagenPlano) {
		this.rutaImagenPlano = rutaImagenPlano;
	}
}

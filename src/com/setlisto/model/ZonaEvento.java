package com.setlisto.model;

import java.math.BigDecimal;

public class ZonaEvento extends AbstractValueObject {

	private Long id;
	private Long EventoId;
	private Long CategoriaAsientoId;
	private String CategoriaAsientoNombre;
	private String SeccionNombre;
	private Integer CapacidadTotal;
	private Integer CapacidadDisponible;
	private BigDecimal PrecioBase;
	private Integer posX;
	private Integer posY;
	private Integer ancho;
	private Integer alto;

	public ZonaEvento() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEventoId() {
		return EventoId;
	}

	public Long getCategoriaAsientoId() {
		return CategoriaAsientoId;
	}

	public String getCategoriaAsientoNombre() {
		return CategoriaAsientoNombre;
	}

	public String getSeccionNombre() {
		return SeccionNombre;
	}

	public Integer getCapacidadTotal() {
		return CapacidadTotal;
	}

	public Integer getCapacidadDisponible() {
		return CapacidadDisponible;
	}

	public BigDecimal getPrecioBase() {
		return PrecioBase;
	}

	public Integer getPosX() {
		return posX;
	}

	public Integer getPosY() {
		return posY;
	}

	public Integer getAncho() {
		return ancho;
	}

	public Integer getAlto() {
		return alto;
	}

	public void setEventoId(Long eventoId) {
		EventoId = eventoId;
	}

	public void setCategoriaAsientoId(Long categoriaAsientoId) {
		CategoriaAsientoId = categoriaAsientoId;
	}

	public void setCategoriaAsientoNombre(String categoriaAsientoNombre) {
		CategoriaAsientoNombre = categoriaAsientoNombre;
	}

	public void setSeccionNombre(String seccionNombre) {
		SeccionNombre = seccionNombre;
	}

	public void setCapacidadTotal(Integer capacidadTotal) {
		CapacidadTotal = capacidadTotal;
	}

	public void setCapacidadDisponible(Integer capacidadDisponible) {
		CapacidadDisponible = capacidadDisponible;
	}

	public void setPrecioBase(BigDecimal precioBase) {
		PrecioBase = precioBase;
	}

	public void setPosX(Integer posX) {
		this.posX = posX;
	}

	public void setPosY(Integer posY) {
		this.posY = posY;
	}

	public void setAncho(Integer ancho) {
		this.ancho = ancho;
	}

	public void setAlto(Integer alto) {
		this.alto = alto;
	}
}

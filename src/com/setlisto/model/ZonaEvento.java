package com.setlisto.model;

import java.math.BigDecimal;

/*
 * Representa un rectangulo dibujado en el plano del evento musical.
 * por el momento la reserva se hara directamente hacia la zona.
 */
public class ZonaEvento extends AbstractValueObject {
	private Long id;
	private Long eventoId;
	private Long categoriaAsientoId;
	private String categoriaAsientoNombre;
	private String seccionNombre;
	private Integer capacidadTotal;
	private Integer capacidadDisponible;
	private BigDecimal precioBase;
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

	public Long getEventoId() {
		return eventoId;
	}

	public Long getCategoriaAsientoId() {
		return categoriaAsientoId;
	}

	public String getCategoriaAsientoNombre() {
		return categoriaAsientoNombre;
	}

	public String getSeccionNombre() {
		return seccionNombre;
	}

	public Integer getCapacidadTotal() {
		return capacidadTotal;
	}

	public Integer getCapacidadDisponible() {
		return capacidadDisponible;
	}

	public BigDecimal getPrecioBase() {
		return precioBase;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	public void setCategoriaAsientoId(Long categoriaAsientoId) {
		this.categoriaAsientoId = categoriaAsientoId;
	}

	public void setCategoriaAsientoNombre(String categoriaAsientoNombre) {
		this.categoriaAsientoNombre = categoriaAsientoNombre;
	}

	public void setSeccionNombre(String seccionNombre) {
		this.seccionNombre = seccionNombre;
	}

	public void setCapacidadTotal(Integer capacidadTotal) {
		this.capacidadTotal = capacidadTotal;
	}

	public void setCapacidadDisponible(Integer capacidadDisponible) {
		this.capacidadDisponible = capacidadDisponible;
	}

	public void setPrecioBase(BigDecimal precioBase) {
		this.precioBase = precioBase;
	}
}

package com.setlisto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ticket extends AbstractValueObject {
	
	private Long id;
	private String codigo;
	private String tipo;
	private BigDecimal precio;
	private LocalDateTime fechaCompra;
	private Long pagoId;
	private Long plazaEventoMusicalId;
	private Long tipoTicketId;
	private Long eventZoneId;
	
	public Ticket() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getPagoId() {
		return pagoId;
	}

	public void setPagoId(Long pagoId) {
		this.pagoId = pagoId;
	}

	public Long getPlazaEventoMusicalId() {
		return plazaEventoMusicalId;
	}

	public void setPlazaEventoMusicalId(Long plazaEventoMusicalId) {
		this.plazaEventoMusicalId = plazaEventoMusicalId;
	}

	public Long getTipoTicketId() {
		return tipoTicketId;
	}

	public void setTipoTicketId(Long tipoTicketId) {
		this.tipoTicketId = tipoTicketId;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public LocalDateTime getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDateTime fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Long getEventZoneId() {
		return eventZoneId;
	}

	public void setEventZoneId(Long eventZoneId) {
		this.eventZoneId = eventZoneId;
	}
}

package com.setlisto.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pago extends AbstractValueObject {
	private Long id;
	private BigDecimal monto;
	private String codigoTransaccion;
	private LocalDateTime fechaPago;
	private Long clienteId;
	private Long estadoPagoId;
	private LocalDateTime fechaCreacion;
	private Long metodoPagoId;
	private Long monedaId;
	
	public Pago() {	
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getCodigoTransaccion() {
		return codigoTransaccion;
	}

	public void setCodigoTransaccion(String codigoTransaccion) {
		this.codigoTransaccion = codigoTransaccion;
	}

	public LocalDateTime getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(LocalDateTime fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getEstadoPagoId() {
		return estadoPagoId;
	}

	public void setEstadoPagoId(Long estadoPagoId) {
		this.estadoPagoId = estadoPagoId;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Long getMetodoPagoId() {
		return metodoPagoId;
	}

	public void setMetodoPagoId(Long metodoPagoId) {
		this.metodoPagoId = metodoPagoId;
	}

	public Long getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
	}
}

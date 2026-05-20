package com.setlisto.model;

public class PagoDTO extends Pago {		
	private String metodoPagoNombre;
	private String monedaSimbolo;
	private String monedaCodigo;
	private String clienteNombre;
	private String estadoPagoNombre;
	
	public PagoDTO() {	
	}
	
	public String getMetodoPagoNombre() {
		return metodoPagoNombre;
	}

	public void setMetodoPagoNombre(String metodoPagoNombre) {
		this.metodoPagoNombre = metodoPagoNombre;
	}

	public String getMonedaSimbolo() {
		return monedaSimbolo;
	}

	public void setMonedaSimbolo(String monedaSimbolo) {
		this.monedaSimbolo = monedaSimbolo;
	}

	public String getMonedaCodigo() {
		return monedaCodigo;
	}

	public void setMonedaCodigo(String monedaCodigo) {
		this.monedaCodigo = monedaCodigo;
	}

	public String getClienteNombre() {
		return clienteNombre;
	}

	public void setClienteNombre(String clienteNombre) {
		this.clienteNombre = clienteNombre;
	}

	public String getEstadoPagoNombre() {
		return estadoPagoNombre;
	}

	public void setEstadoPagoNombre(String estadoPagoNombre) {
		this.estadoPagoNombre = estadoPagoNombre;
	}
}

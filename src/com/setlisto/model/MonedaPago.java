package com.setlisto.model;

public class MonedaPago extends AbstractValueObject{
	private Long id;
	private String codigo;
	private String simbolo;
	
	public MonedaPago() {
	}

	public Long getId() {
		return id;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getSimbolo() {
		return simbolo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}
}

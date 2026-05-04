package com.setlisto.model;

public class Plaza extends AbstractValueObject {
	
	private Long id;
	private Integer fila;
	private Integer numeroAsiento;
	private Long lugarId;
	private Long categoriaId;
	
	public Plaza() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getFila() {
		return fila;
	}

	public void setFila(Integer fila) {
		this.fila = fila;
	}

	public Integer getNumeroAsiento() {
		return numeroAsiento;
	}

	public void setNumeroAsiento(Integer numeroAsiento) {
		this.numeroAsiento = numeroAsiento;
	}

	public Long getLugarId() {
		return lugarId;
	}

	public void setLugarId(Long lugarId) {
		this.lugarId = lugarId;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(Long categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	
	
}

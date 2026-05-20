package com.setlisto.model;

import java.util.List;

/**
 * Clase genérica utilizada como contenedor estándar para respuestas paginadas.
 * Facilita el transporte de datos desde la capa de persistencia hacia la capa de vista
 * o cliente, manteniendo la información de los elementos y el conteo total.
 * 
 * @param <T> El tipo de objeto contenido en la página (ej. Artista, Cancion, etc.).
 */
public class Results<T> {
	
	/** Lista de elementos de tipo T que conforman la página actual. */
	private List<T> page = null;
	
	/** Cantidad total de registros disponibles en la consulta (independiente del tamaño de la página). */
	private int total = 0;
	
	public Results() {		
	}
	
	/**
	 * Obtiene el total de registros disponibles.
	 * @return El número total de registros.
	 */
	public int getTotal() {
		return total;
	}
	
	/**
	 * Establece el total de registros encontrados.
	 * @param total Número total de registros.
	 */
	public void setTotal(int total) {
		this.total = total;
	}
	
	/**
	 * Obtiene la lista de elementos de la página actual.
	 * @return Lista de objetos de tipo T.
	 */
	public List<T> getPage() {
		return page;
	}

	/**
	 * Establece la lista de elementos para la página actual.
	 * @param page Lista de objetos de tipo T.
	 */
	public void setPage(List<T> page) {
		this.page = page;
	}
}
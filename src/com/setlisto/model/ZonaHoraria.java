package com.setlisto.model;

/*
 * Zona horaria del lugar del evento, para en un futuro poner comparativa entre zona
 *  horaria del lugar y la zona horaria del cliente que ve el evento
 */
public class ZonaHoraria extends AbstractValueObject {
	private Long id = null;
	private String nombre = null;

	public ZonaHoraria() {
	}
	
	public ZonaHoraria(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
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
}

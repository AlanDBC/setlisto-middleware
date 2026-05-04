package com.setlisto.model;

public class SubGeneroEnEventoMusical extends AbstractValueObject {

	private Long eventoMusicalId;
	private Long subgeneroMusicalId;

	public SubGeneroEnEventoMusical() {
	}

	public Long getEventoMusicalId() {
		return eventoMusicalId;
	}

	public void setEventoMusicalId(Long eventoMusicalid) {
		this.eventoMusicalId = eventoMusicalid;
	}

	public Long getSubgeneroMusicalId() {
		return subgeneroMusicalId;
	}

	public void setSubgeneroMusicalId(Long subgeneroMusicalId) {
		this.subgeneroMusicalId = subgeneroMusicalId;
	}
}

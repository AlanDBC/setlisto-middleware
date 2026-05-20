package com.setlisto.criteria;

public class PlazaEnEventoCriteria {	
	private final String ORDER_BY_ID = " sme.id ";
	private final String ORDER_BY_EVENTO_MUSICAL_NAME = " me.name ";
	private final String ORDER_BY_SEAT_ROW = " st.row ";
	private final String ORDER_BY_SEAT_NUMBER = " st.number";
	private final String ORDER_BY_SEAT_STATUS_NAME = " se.name ";
	
	private Long id;
	private Long eventoMusicalId;
	private Long estatusId;
	private Long plazaId;
	private String orderBy;
    private Boolean ascDesc = true;

	public PlazaEnEventoCriteria() {
	}

	public Long getId() {
		return id;
	}

	public Long getEventoMusicalId() {
		return eventoMusicalId;
	}

	public Long getEstatusId() {
		return estatusId;
	}

	public Long getPlazaId() {
		return plazaId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEventoMusicalId(Long eventoMusicalId) {
		this.eventoMusicalId = eventoMusicalId;
	}

	public void setEstatusId(Long estatusId) {
		this.estatusId = estatusId;
	}

	public void setPlazaId(Long plazaId) {
		this.plazaId = plazaId;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public Boolean getAscDesc() {
		return ascDesc;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setAscDesc(Boolean ascDesc) {
		this.ascDesc = ascDesc;
	}
}

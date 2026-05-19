package com.setlisto.criteria;

public class LugarCriteria {	
	private final String ORDER_BY_ID = "s.id";
	private final String ORDER_BY_NAME = "s.name";
	private final String ORDER_BY_CITY = "c.name";
	private final String ORDER_BY_REGION = "r.name";
	private final String ORDER_BY_DIRECCION = "s.address";
	// Geograficos
	private Long paisId = null;
	private Long regionId = null;
	private Long ciudadId= null;
	private String orderBy;
    private Boolean ascDesc = true;
    
    private String lugarNombre = null;
    private String lugarDireccion = null;
    
    public LugarCriteria() {
	}
    
    public String getLugarNombre() {
		return lugarNombre;
	}

	public String getLugarDireccion() {
		return lugarDireccion;
	}

	public void setLugarNombre(String lugarNombre) {
		this.lugarNombre = lugarNombre;
	}

	public void setLugarDireccion(String lugarDireccion) {
		this.lugarDireccion = lugarDireccion;
	}

	public Long getPaisId() {
		return paisId;
	}
	public void setPaisId(Long paisId) {
		this.paisId = paisId;
	}
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public Long getCiudadId() {
		return ciudadId;
	}
	public void setCiudadId(Long ciudadId) {
		this.ciudadId = ciudadId;
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

package com.setlisto.criteria;

public class PlazaCriteria {	
	private final String ORDER_BY_ID = "s.id";
	private final String ORDER_BY_SITE_NAME = "st.name";
	private final String ORDER_BY_CATEGORY_NAME = "sc.name";
	private final String ORDER_BY_ROW = "s.row";
	private final String ORDER_BY_NUMBER = "s.number";
	private final String ORDER_BY_CATEGORY_ID = "sc.id";
	
	private Long lugarId;
	private Long categoriaId;
	private String orderBy;
    private Boolean ascDesc = true;

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

	public PlazaCriteria() {
	}

	public Long getLugarId() {
		return lugarId;
	}

	public Long getCategoriaId() {
		return categoriaId;
	}

	public void setLugarId(Long siteId) {
		this.lugarId = siteId;
	}

	public void setCategoriaId(Long categoryId) {
		this.categoriaId = categoryId;
	}
}

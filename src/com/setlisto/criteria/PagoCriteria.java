package com.setlisto.criteria;

import java.util.Date;

public class PagoCriteria {
		private final String ORDER_BY_ID = " p.id ";
		private final String ORDER_BY_AMOUNT = " p.amount ";
		private final String ORDER_BY_TRANSACTION_CODE = " p.transaction_code ";
		private final String ORDER_BY_CREATED_AT = " p.created_at ";
		private final String ORDER_BY_PAYMENT_DATE = " p.payment_date ";
		private final String ORDER_BY_CUSTOMER_NAME = " c.name ";
		private final String ORDER_BY_PAYMENT_STATUS_NAME = " ps.name ";
	
	    private Long clienteId;
	    
	    private Long monedaId ;
	    private Long estadoPagoId;
	    private Long metodoPagoId;
	    
	    private Date fechaDesde;
	    private Date fechaHasta;
	    
	    private String orderBy;
	    private Boolean ascDesc = true;

	public PagoCriteria() {
	}

	public Long getClienteId() {
		return clienteId;
	}

	public Long getEstadoPagoId() {
		return estadoPagoId;
	}

	public Long getMetodoPagoId() {
		return metodoPagoId;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public Long getMonedaId() {
		return monedaId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public void setEstadoPagoId(Long estadoPagoId) {
		this.estadoPagoId = estadoPagoId;
	}

	public void setMetodoPagoId(Long metodoPagoId) {
		this.metodoPagoId = metodoPagoId;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public void setMonedaId(Long monedaId) {
		this.monedaId = monedaId;
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

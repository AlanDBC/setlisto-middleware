package com.setlisto.criteria;

import java.time.LocalDateTime;

public class TicketCriteria {	
	private final String ORDER_BY_ID = " t.id ";
	private final String ORDER_BY_CODE = " t.code ";
	private final String ORDER_BY_PRICE = " t.price ";
	private final String ORDER_BY_PURCHASE_DATE = " t.purchase_date ";
	private final String ORDER_BY_PAYMENT_ID = " t.payment_id ";
	private final String ORDER_BY_CUSTOMER_NAME = "c.name";
	private final String ORDER_BY_MUSICAL_EVENT_NAME = "me.name";
	private final String ORDER_BY_MUSICAL_EVENT_START_DATE = "me.start_date";
	private final String ORDER_BY_SITE_NAME = "ste.name";
	private final String ORDER_BY_SITE_ADDRESS = "ste.address";
	private final String ORDER_BY_SEAT_ROW = "st.row";
	private final String ORDER_BY_SEAT_NUMBER = "st.number";
	private final String ORDER_BY_SEAT_CATEGORY_NAME = "stc.name";	
	private final String ORDER_BY_SEAT_STATUS_NAME = "sts.name";
	private final String ORDER_BY_TICKET_TYPE_NAME = "tty.name";

	private Long clienteId = null;
	private Long eventoId = null;
	private Long pagoId = null;
	private LocalDateTime desde = null;
	private LocalDateTime hasta = null;
	private String orderBy;
	private Boolean ascDesc = true; // true para ascendente, false para descendente

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

	public TicketCriteria() {
		this.orderBy = ORDER_BY_PURCHASE_DATE;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public Long getEventoId() {
		return eventoId;
	}

	public Long getPagoId() {
		return pagoId;
	}

	public LocalDateTime getDesde() {
		return desde;
	}

	public LocalDateTime getHasta() {
		return hasta;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public void setEventoId(Long eventoId) {
		this.eventoId = eventoId;
	}

	public void setPagoId(Long pagoId) {
		this.pagoId = pagoId;
	}

	public void setDesde(LocalDateTime desde) {
		this.desde = desde;
	}

	public void setHasta(LocalDateTime hasta) {
		this.hasta = hasta;
	}
}

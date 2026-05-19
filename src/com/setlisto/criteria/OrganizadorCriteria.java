package com.setlisto.criteria;

import java.sql.Date;

public class OrganizadorCriteria {
	private final String ORDER_BY_ID = " org.id ";
	private final String ORDER_BY_NOMBRE_COMERCIAL = " org.business_name ";
	private final String ORDER_BY_VERIFICADO = " org.verified ";
	private final String ORDER_BY_EMAIL = " org.email ";
	private final String ORDER_BY_TELEFONO = " org.phone ";
	private final String ORDER_BY_NOMBRE = " org.name ";
	private final String ORDER_BY_APELLIDO1 = " org.surname1 ";
	private final String ORDER_BY_APELLIDO2 = " org.surname2 ";
	private final String ORDER_BY_FECHA_NACIMIENTO = " org.birth_date ";

	private Long id = null;
	private String nombreComercial = null;
	private Boolean verificado = null;
	private String email = null;
	private String telefono = null;
	private String nombre = null;
	private String apellido1 = null;
	private String apellido2 = null;
	private Date fechaNacimientoDesde = null;
	private Date fechaNacimientoHasta = null;
	private String orderBy;
    private Boolean ascDesc = true;
	
	public OrganizadorCriteria() {
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFechaNacimientoDesde() {
		return fechaNacimientoDesde;
	}

	public void setFechaNacimientoDesde(Date fechaNacimientoDesde) {
		this.fechaNacimientoDesde = fechaNacimientoDesde;
	}

	public Date getFechaNacimientoHasta() {
		return fechaNacimientoHasta;
	}

	public void setFechaNacimientoHasta(Date fechaNacimientoHasta) {
		this.fechaNacimientoHasta = fechaNacimientoHasta;
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

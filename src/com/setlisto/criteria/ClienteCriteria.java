package com.setlisto.criteria;

import java.sql.Date;
import java.time.LocalDateTime;

public class ClienteCriteria {
	
	private final String ORDER_BY_ID = " id ";
	private final String ORDER_BY_EMAIL = " email ";
	private final String ORDER_BY_NOMBRE = " name ";
	private final String ORDER_BY_APELLIDO = " surname1 ";
	private final String ORDER_BY_TELEFONO = " phone ";
	private final String ORDER_BY_ACTIVO = " active ";
	private final String ORDER_BY_VERIFICADO = " verified ";
	private final String ORDER_BY_CREADO = " created_at ";
	private final String ORDER_BY_FECHA_NACIMIENTO = " birth_date ";
	
    private String email;
    private String nombre;
    private String apellido;
    private String telefono;

    private Boolean activo;
    private Boolean verificado;

    private LocalDateTime creadoDesde;
    private LocalDateTime creadoHasta;
    
    private Date fechaNacimientoDesde;
    private Date fechaNacimientoHasta;
    
    private String orderBy;
    private Boolean ascDesc = true; // true para ascendente, false para descendente

	public ClienteCriteria() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

	public LocalDateTime getCreadoDesde() {
		return creadoDesde;
	}

	public void setCreadoDesde(LocalDateTime creadoDesde) {
		this.creadoDesde = creadoDesde;
	}

	public LocalDateTime getCreadoHasta() {
		return creadoHasta;
	}

	public void setCreadoHasta(LocalDateTime creadoHasta) {
		this.creadoHasta = creadoHasta;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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

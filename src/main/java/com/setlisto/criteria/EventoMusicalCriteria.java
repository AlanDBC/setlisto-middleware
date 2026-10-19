package com.setlisto.criteria;

import java.time.LocalDateTime;
import java.util.List;

import com.setlisto.model.AbstractValueObject;

public class EventoMusicalCriteria extends AbstractValueObject {
	public final String ORDER_BY_NAME = " me.name ";
	public final String ORDER_BY_DATE = " me.start_date "; 
	public final String ORDER_BY_PRICE = " t.price ";      
	public final String ORDER_BY_CAPACITY = " me.capacity "; 
	public final String ORDER_BY_ARTIST = " artist_names "; // Alias del GROUP_CONCAT
	public final String ORDER_BY_LUGAR = " st.name ";       
	public final String ORDER_BY_CIUDAD = " ct.name ";      
	public final String ORDER_BY_REGION = " rg.name ";

	private Long id = null;
	private String nombre = null;
	private LocalDateTime fechaInicio = null;
	private LocalDateTime fechaFin = null;
	private Integer capacidadDesde = null;
	private Integer capacidadHasta = null;

	List<Long> subTiposEventosIds;
	List<Long> tiposEventosIds;

	List<Long> subGenerosMusicalesIds;
	List<Long> generosMusicalesIds;

	private Long artistaId = null;
	private String artistaNombre = null;

	private Long organizadorId = null;
	private Long lugarId = null;

	List<Long> ciudadesIds = null;
	List<Long> regionesIds = null;
	List<Long> paisesIds = null;

	private Integer precioDesde = null;
	private Integer precioHasta = null;

	private Long estadoId = null;
	private Long zonaHorariaId = null;

	private String orderBy = ORDER_BY_NAME; // Por defecto ordenamos por nombre
	private Boolean ascDesc = true; 

	public EventoMusicalCriteria() {
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

	public Integer getCapacidadDesde() {
		return capacidadDesde;
	}

	public void setCapacidadDesde(Integer capacidadDesde) {
		this.capacidadDesde = capacidadDesde;
	}

	public Integer getCapacidadHasta() {
		return capacidadHasta;
	}

	public void setCapacidadHasta(Integer capacidadHasta) {
		this.capacidadHasta = capacidadHasta;
	}

	public Long getArtistaId() {
		return artistaId;
	}

	public void setArtistaId(Long artistaId) {
		this.artistaId = artistaId;
	}

	public String getArtistaNombre() {
		return artistaNombre;
	}

	public void setArtistaNombre(String artistaNombre) {
		this.artistaNombre = artistaNombre;
	}

	public Long getOrganizadorId() {
		return organizadorId;
	}

	public void setOrganizadorId(Long organizadorId) {
		this.organizadorId = organizadorId;
	}

	public Long getLugarId() {
		return lugarId;
	}

	public void setLugarId(Long lugarId) {
		this.lugarId = lugarId;
	}

	public Long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Long estadoId) {
		this.estadoId = estadoId;
	}

	public Integer getPrecioDesde() {
		return precioDesde;
	}

	public void setPrecioDesde(Integer precioDesde) {
		this.precioDesde = precioDesde;
	}

	public Integer getPrecioHasta() {
		return precioHasta;
	}

	public void setPrecioHasta(Integer precioHasta) {
		this.precioHasta = precioHasta;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Long getZonaHorariaId() {
		return zonaHorariaId;
	}

	public void setZonaHorariaId(Long zonaHorariaId) {
		this.zonaHorariaId = zonaHorariaId;
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

	public List<Long> getSubTiposEventosIds() {
		return subTiposEventosIds;
	}

	public void setSubTiposEventosIds(List<Long> subTiposEventosIds) {
		this.subTiposEventosIds = subTiposEventosIds;
	}

	public List<Long> getSubGenerosMusicalesIds() {
		return subGenerosMusicalesIds;
	}

	public void setSubGenerosMusicalesIds(List<Long> subGenerosMusicalesIds) {
		this.subGenerosMusicalesIds = subGenerosMusicalesIds;
	}

	public List<Long> getTiposEventosIds() {
		return tiposEventosIds;
	}

	public List<Long> getGenerosMusicalesIds() {
		return generosMusicalesIds;
	}

	public List<Long> getCiudadesIds() {
		return ciudadesIds;
	}

	public List<Long> getRegionesIds() {
		return regionesIds;
	}

	public List<Long> getPaisesIds() {
		return paisesIds;
	}

	public void setTiposEventosIds(List<Long> tiposEventosIds) {
		this.tiposEventosIds = tiposEventosIds;
	}

	public void setGenerosMusicalesIds(List<Long> generosMusicalesIds) {
		this.generosMusicalesIds = generosMusicalesIds;
	}

	public void setCiudadesIds(List<Long> ciudadesIds) {
		this.ciudadesIds = ciudadesIds;
	}

	public void setRegionesIds(List<Long> regionesIds) {
		this.regionesIds = regionesIds;
	}

	public void setPaisesIds(List<Long> paisesIds) {
		this.paisesIds = paisesIds;
	}
}

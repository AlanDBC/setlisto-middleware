package com.setlisto.criteria;

import java.time.LocalDateTime;

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
    // Filtrados de tipo
    private Long subtipoEventoId = null;
    private Long tipoEventoId = null;
    // Filtrados de géneros
    private Long subGeneroMusicalId = null;
    private Long generoMusicalId = null;
    // Filtrado por Artista
    private Long artistaId = null;
    private String artistaNombre = null;
    // Filtrado por Organizador y Recinto
    private Long organizadorId = null;
    private Long lugarId = null;
    // Filtrados Geográficos (derivados de lugar)
    private Long ciudadId = null;
    private Long regionId = null;
    private Long paisId = null;
    // Filtrados de precio
    private Integer precioDesde = null;
    private Integer precioHasta = null;
    // filtrado de estado
    private Long estadoId = null;
    private Long zonaHorariaId = null;
    
    private String orderBy = ORDER_BY_NAME; // Por defecto ordenamos por nombre
    private Boolean ascDesc = true; 		// true para ascendente, false para descendente

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

	public Long getTipoEventoId() {
		return tipoEventoId;
	}

	public void setTipoEventoId(Long tipoEventoId) {
		this.tipoEventoId = tipoEventoId;
	}

	public Long getSubtipoEventoId() {
		return subtipoEventoId;
	}

	public void setSubtipoEventoId(Long subTipoEventoId) {
		this.subtipoEventoId = subTipoEventoId;
	}

	public Long getGeneroMusicalId() {
		return generoMusicalId;
	}

	public void setGeneroMusicalId(Long generoMusicalId) {
		this.generoMusicalId = generoMusicalId;
	}

	public Long getSubGeneroMusicalId() {
		return subGeneroMusicalId;
	}

	public void setSubGeneroMusicalId(Long subGeneroMusicalId) {
		this.subGeneroMusicalId = subGeneroMusicalId;
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

	public Long getCiudadId() {
		return ciudadId;
	}

	public void setCiudadId(Long ciudadId) {
		this.ciudadId = ciudadId;
	}

	public Long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(Long estadoId) {
		this.estadoId = estadoId;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getPaisId() {
		return paisId;
	}

	public void setPaisId(Long paisId) {
		this.paisId = paisId;
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
}

package com.setlisto.service;

import java.util.List;
import java.util.Map;

import com.setlisto.model.Ciudad;
import com.setlisto.model.GeneroMusical;
import com.setlisto.model.Pais;
import com.setlisto.model.Region;
import com.setlisto.model.SubGeneroMusicalDTO;
import com.setlisto.model.SubTipoEventoDTO;
import com.setlisto.model.TipoEvento;

public interface FiltroService {
    // Jerarquía de 2 niveles: Género -> Subgéneros
    Map<GeneroMusical, List<SubGeneroMusicalDTO>> obtenerGenerosJerarquicos() throws ServiceException;
    
    // Jerarquía de 2 niveles: Tipo de Evento -> Subtipos
    Map<TipoEvento, List<SubTipoEventoDTO>> obtenerTiposEventoJerarquicos() throws ServiceException;
    
    // Jerarquía de 3 niveles: País -> Región -> Ciudades
    Map<Pais, Map<Region, List<Ciudad>>> obtenerUbicacionesJerarquicas() throws ServiceException;
}

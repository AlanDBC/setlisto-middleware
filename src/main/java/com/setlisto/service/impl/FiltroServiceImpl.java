package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.setlisto.dao.*;
import com.setlisto.model.*;
import com.setlisto.service.FiltroService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class FiltroServiceImpl implements FiltroService {

    private GeneroMusicalDAO generoDAO = new GeneroMusicalDAO();
    private SubGeneroMusicalDAO subGeneroDAO = new SubGeneroMusicalDAO();
    
    private TipoEventoDAO tipoEventoDAO = new TipoEventoDAO();
    private SubTipoEventoDAO subTipoEventoDAO = new SubTipoEventoDAO();
    
    private PaisDAO paisDAO = new PaisDAO();
    private RegionDAO regionDAO = new RegionDAO();
    private CiudadDAO ciudadDAO = new CiudadDAO();

    @Override
    public Map<GeneroMusical, List<SubGeneroMusicalDTO>> obtenerGenerosJerarquicos() throws ServiceException {
        Connection c = null;
        // Usamos LinkedHashMap para mantener el orden en el que vienen de la base de datos
        Map<GeneroMusical, List<SubGeneroMusicalDTO>> mapa = new LinkedHashMap<>();
        
        try {
            c = JDBCUtils.getConnection();
            
            // 1. Traer todos los géneros padres
            List<GeneroMusical> generos = generoDAO.findAll(c);
            
            // 2. Iterar y buscar los hijos de cada padre
            for (GeneroMusical genero : generos) {
                List<SubGeneroMusicalDTO> subgeneros = subGeneroDAO.findByGeneroId(c, genero.getId());
                mapa.put(genero, subgeneros);
            }
            
            return mapa;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener la jerarquía de géneros musicales", e);
        } finally {
            JDBCUtils.close(c);
        }
    }

    @Override
    public Map<TipoEvento, List<SubTipoEventoDTO>> obtenerTiposEventoJerarquicos() throws ServiceException {
        Connection c = null;
        Map<TipoEvento, List<SubTipoEventoDTO>> mapa = new LinkedHashMap<>();
        
        try {
            c = JDBCUtils.getConnection();
            List<TipoEvento> tipos = tipoEventoDAO.findAll(c);
            
            for (TipoEvento tipo : tipos) {
                List<SubTipoEventoDTO> subtipos = subTipoEventoDAO.findByTipoEventoId(c, tipo.getId());
                mapa.put(tipo, subtipos);
            }
            
            return mapa;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener la jerarquía de tipos de evento", e);
        } finally {
            JDBCUtils.close(c);
        }
    }

    @Override
    public Map<Pais, Map<Region, List<Ciudad>>> obtenerUbicacionesJerarquicas() throws ServiceException {
        Connection c = null;
        // Estructura de 3 niveles: País -> (Región -> Ciudades)
        Map<Pais, Map<Region, List<Ciudad>>> mapaPaises = new LinkedHashMap<>();
        
        try {
            c = JDBCUtils.getConnection();
            
            List<Pais> paises = paisDAO.findAll(c);
            
            for (Pais pais : paises) {
                Map<Region, List<Ciudad>> mapaRegiones = new LinkedHashMap<>();
                List<Region> regiones = regionDAO.findByPaisId(c, pais.getId());
                
                for (Region region : regiones) {
                    List<Ciudad> ciudades = ciudadDAO.findByRegionId(c, region.getId());
                    mapaRegiones.put(region, ciudades);
                }
                
                mapaPaises.put(pais, mapaRegiones);
            }
            
            return mapaPaises;
        } catch (Exception e) {
            throw new ServiceException("Error al obtener la jerarquía de ubicaciones", e);
        } finally {
            JDBCUtils.close(c);
        }
    }
}
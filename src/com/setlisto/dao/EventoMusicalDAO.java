package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.EventoMusicalCriteria;
import com.setlisto.model.Artista;
import com.setlisto.model.EventoMusical;
import com.setlisto.model.EventoMusicalDTO;
import com.setlisto.model.GeneroMusical;
import com.setlisto.model.Results;
import com.setlisto.model.SubGeneroMusical;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

/**
 * Ultimos cambios: Se agregaron campos de List<> para artistas, generos y subgéneros en EventoMusicalDTO.
 * Se modificó el SQL para traer esta información utilizando GROUP_CONCAT y se agregaron 
 * métodos para parsear estas cadenas concatenadas y llenar las listas correspondientes en el DTO. 
 * Además, se mejoró la construcción dinámica del SQL en el método findByCriteria para manejar correctamente los filtros
 * por artista, género y subgénero sin excluir otros posibles valores relacionados al evento.
 */

public class EventoMusicalDAO {

	private static Logger logger = LogManager.getLogger(EventoMusicalDAO.class.getName()); // TODO
	
	private final String BASE_QUERY =
	        " SELECT me.id, me.name, me.description, me.start_date, me.end_date, me.organizer_id, "
	                + " org.business_name, me.site_id, st.name, st.address, ct.id, ct.name, es.event_type_id, "
	                + " et.name, me.event_subtype_id, es.name, me.capacity, evs.id, evs.name, me.time_zone_id, tz.name, "
	                // Artistas
	                + " GROUP_CONCAT(DISTINCT art.id SEPARATOR ';;') AS artist_ids, "
	                + " GROUP_CONCAT(DISTINCT art.name SEPARATOR ';;') AS artist_names, "
	                // Subgéneros
	                + " GROUP_CONCAT(DISTINCT msg.id SEPARATOR ';;') AS subgenre_ids, "
	                + " GROUP_CONCAT(DISTINCT msg.name SEPARATOR ';;') AS subgenre_names, "
	                // Géneros
	                + " GROUP_CONCAT(DISTINCT mg.id SEPARATOR ';;') AS genre_ids, "
	                + " GROUP_CONCAT(DISTINCT mg.name SEPARATOR ';;') AS genre_names "
	                + " FROM musical_event me "
	                + " INNER JOIN organizer org ON me.organizer_id = org.id "
	                + " INNER JOIN site st ON me.site_id = st.id "
	                + " INNER JOIN city ct ON st.city_id = ct.id "
	                + " INNER JOIN region rg ON ct.region_id = rg.id "      
	                + " INNER JOIN country co ON rg.country_id = co.id "  
	                + " INNER JOIN event_subtype es ON es.id = me.event_subtype_id "
	                + " INNER JOIN event_type et ON es.event_type_id = et.id "
	                // Relaciones de Géneros y Subgéneros
	                + " LEFT JOIN musical_event_subgenre mes ON mes.musical_event_id = me.id "
	                + " LEFT JOIN musical_subgenre msg ON msg.id = mes.musical_subgenre_id "
	                + " LEFT JOIN musical_genre mg ON msg.musical_genre_id = mg.id "
	                // Otras relaciones
	                + " LEFT JOIN musical_event_artist mea ON mea.musical_event_id = me.id "
	                + " LEFT JOIN artist art ON art.id = mea.artist_id "
	                + " LEFT JOIN seat_of_musical_event som ON som.musical_event_id = me.id "
	                + " LEFT JOIN ticket t ON t.seat_of_musical_event_id = som.id "
	                + " INNER JOIN event_status evs ON evs.id = me.event_status_id "
	                + " INNER JOIN time_zone tz ON tz.id = me.time_zone_id ";
	
	private final String BASE_GROUP_BY = 
	        " GROUP BY me.id, me.name, me.description, me.start_date, me.end_date, me.organizer_id, "
	        + " org.business_name, me.site_id, st.name, st.address, ct.id, ct.name, es.event_type_id, "
	        + " et.name, me.event_subtype_id, es.name, me.capacity, evs.id, evs.name, me.time_zone_id, tz.name ";

	public EventoMusicalDAO() {
	}

	public EventoMusicalDTO findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE me.id = ? ");
			sql.append(BASE_GROUP_BY); // Para que funcionen los GROUP_CONCAT aunque en este caso no deberían traer múltiples registros por ser la búsqueda por ID

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			EventoMusicalDTO em = null;
			if (rs.next()) {
				em = loadNext(rs);
			}
			return em;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public Results<EventoMusicalDTO> findByCriteria(EventoMusicalCriteria criteria, int from, int pageSize) {
		logger.info("Criteria: {}", criteria); // TODO 

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Results<EventoMusicalDTO> results = new Results<EventoMusicalDTO>(); // TODO
		
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<String>();
			List<Object> parametros = new ArrayList<Object>();

			SQLUtils.addClause(criteria.getId(),condiciones," me.id = ? ",parametros,criteria.getId());
			if (criteria.getId()== null) {				
				SQLUtils.addClause(criteria.getNombre(),condiciones," UPPER(me.name) LIKE UPPER(?) ",parametros,"%"+criteria.getNombre()+"%");
				SQLUtils.addClause(criteria.getFechaInicio(), condiciones, " me.start_date >= ? ", parametros, criteria.getFechaInicio());
				SQLUtils.addClause(criteria.getFechaFin(), condiciones, " me.end_date <= ? ", parametros, criteria.getFechaFin());
				SQLUtils.addClause(criteria.getCapacidadDesde(),condiciones," me.capacity >= ? ",parametros,criteria.getCapacidadDesde());
				SQLUtils.addClause(criteria.getCapacidadHasta(),condiciones," me.capacity <= ? ",parametros,criteria.getCapacidadHasta());
				SQLUtils.addClause(criteria.getSubtipoEventoId(),condiciones," me.event_subtype_id = ? ",parametros,criteria.getSubtipoEventoId());
				SQLUtils.addClause(criteria.getTipoEventoId(),condiciones," es.event_type_id = ? ",parametros,criteria.getTipoEventoId());
				
				// Filtro por Subgénero: Buscamos eventos que tengan asignado este subgénero específico sin excluir otros subgeneros
				SQLUtils.addClause(criteria.getSubGeneroMusicalId(), condiciones, 
						" me.id IN (SELECT mesg_filter.musical_event_id FROM musical_event_subgenre mesg_filter WHERE mesg_filter.musical_subgenre_id = ?) ", 
						parametros, criteria.getSubGeneroMusicalId());
				
				// Filtro por Género: Buscamos eventos que tengan subgéneros que pertenezcan a este género sin excluir otros géneros 
				SQLUtils.addClause(criteria.getGeneroMusicalId(), condiciones, 
				    " me.id IN (SELECT mesg_filter.musical_event_id " +
				    "           FROM musical_event_subgenre mesg_filter " +
				    "           JOIN musical_subgenre msg_filter ON mesg_filter.musical_subgenre_id = msg_filter.id " +
				    "           WHERE msg_filter.musical_genre_id = ?) ", 
				    parametros, criteria.getGeneroMusicalId());
				
				SQLUtils.addClause(criteria.getOrganizadorId(), condiciones, " me.organizer_id = ? ", parametros, criteria.getOrganizadorId());
				SQLUtils.addClause(criteria.getLugarId(), condiciones, " me.site_id = ? ", parametros, criteria.getLugarId());
				SQLUtils.addClause(criteria.getCiudadId(), condiciones, " ct.id = ? ", parametros, criteria.getCiudadId());
				SQLUtils.addClause(criteria.getRegionId(), condiciones, " rg.id = ? ", parametros, criteria.getRegionId());
				SQLUtils.addClause(criteria.getPaisId(), condiciones, " co.id = ? ", parametros, criteria.getPaisId());
				SQLUtils.addClause(criteria.getPrecioDesde(), condiciones, " t.price >= ? ", parametros, criteria.getPrecioDesde());
				SQLUtils.addClause(criteria.getPrecioHasta(), condiciones, " t.price <= ? ", parametros, criteria.getPrecioHasta());
				
				// En lugar de filtrar por el alias 'art.id', filtramos por el ID del evento que contenga a dicho artista y no excluir artistas
				SQLUtils.addClause(criteria.getArtistaId(), condiciones,
						" me.id IN (SELECT mea_filter.musical_event_id FROM musical_event_artist mea_filter WHERE mea_filter.artist_id = ?) ",
						parametros, criteria.getArtistaId());
				
				// De igual manera para el nombre del artista, se busca que el evento tenga asignado un artista con ese nombre sin excluir otros artistas
				SQLUtils.addClause(criteria.getArtistaNombre(), condiciones, 
					    " me.id IN (SELECT mea_f.musical_event_id FROM musical_event_artist mea_f " +
					    "           JOIN artist art_f ON mea_f.artist_id = art_f.id " +
					    "           WHERE UPPER(art_f.name) LIKE UPPER(?)) ", 
					    parametros, "%" + criteria.getArtistaNombre() + "%");
				
				SQLUtils.addClause(criteria.getEstadoId(), condiciones, " me.event_status_id = ? ", parametros, criteria.getEstadoId());
				SQLUtils.addClause(criteria.getZonaHorariaId(), condiciones, " me.time_zone_id = ? ", parametros, criteria.getZonaHorariaId());
			}

			if (!condiciones.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(String.join(" AND ", condiciones) );
			}
			// PRIMERO AGRUPAMOS (Para que los GROUP_CONCAT funcionen)
			sql.append(BASE_GROUP_BY);

			// DESPUÉS ORDENAMOS
			sql.append(" ORDER BY ");
			String orderBy = criteria.getOrderBy();
			// Manejo especial para el precio por ser una relación 1:N
			if (" t.price ".equals(orderBy)) {
			    sql.append(" MIN(t.price) "); 
			} else {
			    sql.append(orderBy);
			}
			sql.append(criteria.getAscDesc() ? " ASC " : " DESC "); // TODO
			
			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}: {}:", criteria, sql);
			} // TODO
			
			ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY); // TODO

			DAOUtils.setParameters(ps, parametros);

			rs = ps.executeQuery();
			List<EventoMusicalDTO> resultsPage = new ArrayList<EventoMusicalDTO>(); // TODO

			int filaInicial = Math.max(1, from + 1); // El ResultSet es 1-based, por comodidad en los criteria usaré  0-based, por eso se suma 1. Además, se asegura que no sea menor a 1 para evitar errores.
			
			if (rs.absolute(filaInicial)) { // Nos movemos a la fila inicial (from) para empezar a cargar resultados desde ahí
				int count = 0;
				do {
					resultsPage.add(loadNext(rs));
					count++;
				} while (count<pageSize && rs.next()); 	// Mientras no se alcance el pageSize y haya más registros, se siguen cargando resultados
			}
			int totalResults = SQLUtils.getTotalRows(rs);
			
			results.setPage(resultsPage); // Se setea la página de resultados (subconjunto de resultados)
			results.setTotal(totalResults); // Se setea el total de resultados // TODO
			
			return results; // TODO
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public EventoMusical create(EventoMusical evento) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO MUSICAL_EVENT (name, description, start_date, end_date, ");
			sql.append(" organizer_id, site_id, event_subtype_id, capacity, event_status_id, time_zone_id) ");
			sql.append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, 1, ?) "); // 1 = Borrador

			ps = c.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);

			DAOUtils.setParameters(ps,
					evento.getNombre(),
					evento.getDescripcion(),
					evento.getFechaInicio(),
					evento.getFechaFin(),
					evento.getIdOrganizador(),
					evento.getIdLugar(),
					evento.getIdSubtipo(),
					evento.getCapacidad(),
					evento.getIdZonaHoraria()
					);

			ps.executeUpdate();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				evento.setId(rs.getLong(1));
			}
			return evento;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public void update(EventoMusical evento) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE MUSICAL_EVENT SET name = ?, description = ?, start_date = ?, end_date = ?, ");
			sql.append(" organizer_id = ?, site_id = ?, capacity = ?, event_subtype_id = ?, event_status_id = ?, time_zone_id = ? ");
			sql.append(" WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps,
					evento.getNombre(),
					evento.getDescripcion(),
					evento.getFechaInicio(),
					evento.getFechaFin(),
					evento.getIdOrganizador(),
					evento.getIdLugar(),
					evento.getCapacidad(),
					evento.getIdSubtipo(),
					evento.getIdEstado(),
					evento.getIdZonaHoraria(),
					evento.getId()
					);

			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps,c);
		}
	}

	public void delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			String sql = "DELETE FROM musical_event WHERE id = ?";
			ps = c.prepareStatement(sql);

			DAOUtils.setParameters(ps, id);
			ps.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
	}

	
	
	private EventoMusicalDTO loadNext(ResultSet rs)  throws Exception {
	    int i = 1;
	    EventoMusicalDTO em = new EventoMusicalDTO();
	    em.setId(rs.getLong(i++));
	    em.setNombre(rs.getString(i++));
	    em.setDescripcion(rs.getString(i++));
	    em.setFechaInicio(rs.getObject(i++, LocalDateTime.class));
	    em.setFechaFin(rs.getObject(i++, LocalDateTime.class));
	    em.setIdOrganizador(rs.getLong(i++));
	    em.setOrganizadorNombre(rs.getString(i++));
	    em.setIdLugar(rs.getLong(i++));
	    em.setLugarNombre(rs.getString(i++));
	    em.setLugarDireccion(rs.getString(i++));
	    em.setIdCiudad(rs.getLong(i++));
	    em.setCiudadNombre(rs.getString(i++));
	    em.setIdTipo(rs.getLong(i++));
	    em.setTipoNombre(rs.getString(i++));
	    em.setIdSubtipo(rs.getLong(i++));
	    em.setSubtipoNombre(rs.getString(i++));
	    em.setCapacidad(rs.getInt(i++));
	    em.setIdEstado(rs.getLong(i++));
	    em.setEstadoNombre(rs.getString(i++));
	    em.setIdZonaHoraria(rs.getLong(i++));
	    em.setZonaHorariaNombre(rs.getString(i++));
	    // --- LÓGICA PARA LLENAR LAS LISTAS ---
	 // 1. Artistas
	    String artistIds = rs.getString("artist_ids");
	    String artistNames = rs.getString("artist_names");
	    em.setArtistas(parseListaGenerica(artistIds, artistNames, (id, nombre) -> {
	        Artista artista = new Artista();
	        artista.setId(id);
	        artista.setNombre(nombre);
	        return artista;
	    }));
	    // 2. Subgéneros
	    String subgenreIds = rs.getString("subgenre_ids");
	    String subgenreNames = rs.getString("subgenre_names");
	    em.setSubGeneros(parseListaGenerica(subgenreIds, subgenreNames, (id, nombre) -> {
	        SubGeneroMusical subGenero = new SubGeneroMusical();
	        subGenero.setId(id);
	        subGenero.setNombre(nombre);
	        return subGenero;
	    }));
	    // 3. Géneros
	    String genreIds = rs.getString("genre_ids");
	    String genreNames = rs.getString("genre_names");
	    em.setGeneros(parseListaGenerica(genreIds, genreNames, (id, nombre) -> {
	        GeneroMusical genero = new GeneroMusical();
	        genero.setId(id);
	        genero.setNombre(nombre);
	        return genero;
	    }));
	    
	    return em;
	}
	
	
	/*
	 * Estos métodos parsean las cadenas concatenadas de IDs y Nombres para crear las listas de Artistas, Subgéneros y Géneros.
	 */
	private <T> List<T> parseListaGenerica(String idsStr, String namesStr, BiFunction<Long, String, T> mapeador) {
	    List<T> lista = new ArrayList<>();
	    
	    // Verificamos que las cadenas no vengan nulas o vacías
	    if (idsStr != null && !idsStr.trim().isEmpty() && namesStr != null) {
	        String[] ids = idsStr.split(";;");
	        String[] names = namesStr.split(";;");
	        
	        for (int j = 0; j < ids.length; j++) {
	            Long id = Long.valueOf(ids[j].trim());
	            // Prevenimos un IndexOutOfBounds en caso de que haya menos nombres que IDs
	            String nombre = (j < names.length) ? names[j].trim() : null;
	            
	            // Usamos la función inyectada para crear y llenar el objeto
	            T objeto = mapeador.apply(id, nombre);
	            lista.add(objeto);
	        }
	    }
	    return lista;
	}
	
} // class

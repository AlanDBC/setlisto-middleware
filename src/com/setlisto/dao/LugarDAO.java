package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.LugarCriteria;
import com.setlisto.model.Lugar;
import com.setlisto.model.LugarDTO;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

public class LugarDAO {

	private static Logger logger = LogManager.getLogger(LugarDAO.class.getName());

	private static final String BASE_QUERY = " SELECT s.id, s.name, s.address, s.city_id, c.name, s.time_zone_id, tz.name "
			+ " FROM SITE s "
			+ " JOIN CITY c ON s.city_id = c.id "
			+ " JOIN REGION r ON c.region_id = r.id "
			+ " JOIN COUNTRY co ON r.country_id = co.id "
			+ " JOIN TIME_ZONE tz ON s.time_zone_id = tz.id ";

	public LugarDAO() {
	}

	public LugarDTO findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" WHERE s.id = ?");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			LugarDTO lgr = null;
			while (rs.next()) {	
				lgr = loadNext(rs);
			}
			return lgr;

		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<LugarDTO> findAll(Connection c) throws Exception {
		List<LugarDTO> resultados = new ArrayList<LugarDTO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				resultados.add( loadNext(rs) );
			}
			return resultados;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Results<LugarDTO> findByCriteria(Connection c, LugarCriteria criteria, int from, int pageSize) throws Exception {
		logger.info("Criteria: {}", criteria);

		PreparedStatement ps = null;
		ResultSet rs = null;

		Results<LugarDTO> results = new Results<LugarDTO>();

		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			SQLUtils.addClause(criteria.getPaisId(), condiciones, " co.id = ? ", parametros, criteria.getPaisId());
			SQLUtils.addClause(criteria.getRegionId(), condiciones, " r.id = ? ", parametros, criteria.getRegionId());
			SQLUtils.addClause(criteria.getCiudadId(), condiciones, " c.id = ? ", parametros, criteria.getCiudadId());
			SQLUtils.addClause(criteria.getLugarNombre(), condiciones, " s.name LIKE ? ", parametros, "%" + criteria.getLugarNombre() + "%");
			SQLUtils.addClause(criteria.getLugarDireccion(), condiciones, " s.address LIKE ? ", parametros, "%" + criteria.getLugarDireccion() + "%");

			if (!condiciones.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(String.join(" AND ", condiciones));
			}

			if (criteria.getOrderBy() != null && !criteria.getOrderBy().trim().isEmpty()) {
			    sql.append(" ORDER BY ");
			    sql.append(criteria.getOrderBy());
			    sql.append(criteria.getAscDesc() ? " ASC " : " DESC ");
			} else {
			    // Opcional: Un orden por defecto para que la paginación sea consistente
			    sql.append(" ORDER BY s.name ASC "); 
			}
			
			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}: {}:", criteria, sql);
			} 
			
			ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			DAOUtils.setParameters(ps, parametros);

			rs = ps.executeQuery();
			List<LugarDTO> resultsPage = new ArrayList<LugarDTO>();

			int filaInicial = Math.max(1, from + 1); 
			if (rs.absolute(filaInicial)) {
			    int count = 0;
			    do {
					resultsPage.add(loadNext(rs));
					count++;
				} while (count<pageSize && rs.next());
			}
			int totalResults = SQLUtils.getTotalRows(rs);
			
			results.setPage(resultsPage); 
			results.setTotal(totalResults); 
			
			return results;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Lugar create(Connection c, Lugar lugar) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "INSERT INTO site (name, address, city_id, time_zone_id) VALUES (?, ?, ?, ?)";

			ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

			DAOUtils.setParameters(ps,
					lugar.getNombre(),
					lugar.getDireccion(),
					lugar.getCiudadId(),
					lugar.getIdZonaHoraria()
					); 

			int rows = ps.executeUpdate(); 

			if (rows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					lugar.setId(rs.getLong(1)); 
				}
				return lugar; 
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return null;
	}

	public void update(Connection c, Lugar lugar) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "UPDATE site SET name = ?, address = ?, city_id = ?, time_zone_id = ? WHERE id = ?";
			ps = c.prepareStatement(sql);

			DAOUtils.setParameters(ps,
					lugar.getNombre(),
					lugar.getDireccion(),
					lugar.getCiudadId(),
					lugar.getIdZonaHoraria(),
					lugar.getId()
					);

			ps.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public void delete(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "DELETE FROM site WHERE id = ?";
			ps = c.prepareStatement(sql);

			DAOUtils.setParameters(ps, id);
			ps.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	private LugarDTO loadNext(ResultSet rs) throws Exception {
		int i = 1;
		LugarDTO lgr = new LugarDTO();
		lgr.setId( rs.getLong(i++) );
		lgr.setNombre( rs.getString(i++) );
		lgr.setDireccion( rs.getString(i++));
		lgr.setCiudadId( rs.getLong(i++));
		lgr.setCiudadNombre(rs.getString(i++));
		lgr.setIdZonaHoraria(rs.getLong(i++));
		lgr.setZonaHorariaNombre(rs.getString(i++));
		return lgr;
	}
} // class

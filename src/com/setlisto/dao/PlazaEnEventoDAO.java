package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PlazaEnEventoCriteria;
import com.setlisto.model.PlazaEnEventoDTO;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

public class PlazaEnEventoDAO {

	private static Logger logger = LogManager.getLogger(PlazaEnEventoDAO.class.getName()); // TODO

	private static String BASE_QUERY = " SELECT sme.id, me.id, me.name, st.id, st.row, st.number, se.id, se.name "
			+ " FROM seat_of_musical_event sme "
			+ " INNER JOIN seat st ON st.id = sme.seat_id "
			+ " INNER JOIN musical_event me ON me.id = sme.musical_event_id "
			+ " INNER JOIN seat_status se ON se.id = sme.seat_status_id ";

	public PlazaEnEventoDAO() {
	}

	public PlazaEnEventoDTO findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE sme.id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps,id);
			rs = ps.executeQuery();

			PlazaEnEventoDTO plaza = null;
			if (rs.next()) {
				plaza = loadNext(rs);
			}
			return plaza;
		}  catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Results<PlazaEnEventoDTO> findByCriteria(Connection c, PlazaEnEventoCriteria criteria, int from, int pageSize) throws Exception {
		logger.info("Criteria: {}", criteria);

		PreparedStatement ps = null;
		ResultSet rs = null;

		Results<PlazaEnEventoDTO> results = new Results<PlazaEnEventoDTO>();
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<Object> parametros = new ArrayList<Object>();
			List<String> condiciones = new ArrayList<String>();

			SQLUtils.addClause(criteria.getId(), condiciones, " sme.id = ? ", parametros, criteria.getId());
			if (criteria.getId() == null) {
				SQLUtils.addClause(criteria.getEventoMusicalId(), condiciones, " sme.musical_event_id = ? ", parametros, criteria.getEventoMusicalId());
				SQLUtils.addClause(criteria.getEstatusId(), condiciones, " sme.seat_status_id = ? ", parametros, criteria.getEstatusId());
				SQLUtils.addClause(criteria.getPlazaId(), condiciones, " sme.seat_id = ? ", parametros, criteria.getPlazaId());	
			}

			if (!condiciones.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(String.join(" AND ", condiciones));
			}

			sql.append(" ORDER BY ");
			sql.append(criteria.getOrderBy());
			sql.append(criteria.getAscDesc() ? " ASC " : " DESC ");

			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}: {}:", criteria, sql);
			} 

			ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			DAOUtils.setParameters(ps, parametros);	

			rs = ps.executeQuery();
			List<PlazaEnEventoDTO> resultsPage = new ArrayList<>();

			if (from>=1) {
				int count = 0;
				rs.absolute(from);
				do {
					resultsPage.add(loadNext(rs));
					count++;
				} while (count<pageSize && rs.next());
			}
			int totalResults = SQLUtils.getTotalRows(rs);

			results.setPage(resultsPage); 
			results.setTotal(totalResults);

			return results;
		}  catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public boolean updateStatus(Connection c, Long plazaEventoMusicalId, Long estatusId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE seat_of_musical_event ");
			sql.append(" SET seat_status_id = ? ");
			sql.append(" WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, estatusId, plazaEventoMusicalId);

			int filasAfectadas = ps.executeUpdate();
			return filasAfectadas > 0;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public boolean isAvailable(Connection c, Long plazaEventoMusicalId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT 1 ");
			sql.append(" FROM seat_of_musical_event ");
			sql.append(" WHERE id = ? ");
			sql.append(" AND seat_status_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, plazaEventoMusicalId, 1L); // 1L corresponde a 'available'

			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return false;
	}

	public int countAvailableByEvent(Connection c, Long eventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) ");
			sql.append(" FROM seat_of_musical_event ");
			sql.append(" WHERE musical_event_id = ? ");
			sql.append(" AND seat_status_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, eventId, 1L); // 1L corresponde a 'available'

			rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return 0;
	}

	private PlazaEnEventoDTO loadNext (ResultSet rs) throws Exception {
		int i = 1;
		PlazaEnEventoDTO dto = new PlazaEnEventoDTO();
		dto.setId(rs.getLong(i++));
		dto.setEventoMusicalId(rs.getLong(i++));
		dto.setEventoMusicalNombre(rs.getString(i++));
		dto.setPlazaId(rs.getLong(i++));
		dto.setPlazaFila(rs.getString(i++));
		dto.setPlazaNumero(rs.getLong(i++));
		dto.setEstadoPlazaId(rs.getLong(i++));
		dto.setEstadoPlazaNombre(rs.getString(i++));
		return dto;
	}
}

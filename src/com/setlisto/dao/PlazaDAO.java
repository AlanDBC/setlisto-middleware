package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PlazaCriteria;
import com.setlisto.model.PlazaDTO;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

public class PlazaDAO {

	private static Logger logger = LogManager.getLogger(PlazaDAO.class.getName());
	
	private static String BASE_QUERY = " SELECT s.id, s.row, s.number, st.id, st.name, sc.id, sc.name "
			+ " FROM seat s "
			+ " INNER JOIN site st ON st.id = s.site_id "
			+ " INNER JOIN seat_category sc ON sc.id = s.seat_category_id ";

	public PlazaDAO() {
	}

	public PlazaDTO findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE s.id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			PlazaDTO plaza = null;
			while (rs.next()) {
				plaza = loadNext(rs);
			}
			return plaza;
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public Results<PlazaDTO> findByCriteria(PlazaCriteria criteria, int from, int pageSize) {
		logger.info("Criteria: {}", criteria);
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Results<PlazaDTO> results = new Results<PlazaDTO>();
		
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<String>();
			List<Object> parametros = new ArrayList<Object>();

			SQLUtils.addClause(criteria.getLugarId(), condiciones, " st.id = ? ", parametros, criteria.getLugarId());
			SQLUtils.addClause(criteria.getCategoriaId(), condiciones, " sc.id = ? ", parametros, criteria.getCategoriaId());

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
			List<PlazaDTO> resultsPage = new ArrayList<PlazaDTO>();

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
		}   catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	private PlazaDTO loadNext (ResultSet rs) throws Exception {
		int i = 1;
		PlazaDTO plaza = new PlazaDTO();
		plaza = new PlazaDTO();
		plaza.setId(rs.getLong(i++));
		plaza.setFila(rs.getInt(i++));
		plaza.setNumeroAsiento(rs.getInt(i++));
		plaza.setLugarId(rs.getLong(i++));
		plaza.setLugarNombre(rs.getString(i++));
		plaza.setCategoriaId(rs.getLong(i++));
		plaza.setCategoriaNombre(rs.getString(i++));
		return plaza;
	}
}
package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.SubGeneroMusical;
import com.setlisto.model.SubGeneroMusicalDTO;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class SubGeneroMusicalDAO {

	private static String BASE_QUERY = "SELECT ms.id, ms.name, ms.musical_genre_id, mg.name FROM musical_subgenre ms "
			+ " JOIN musical_genre mg ON ms.musical_genre_id = mg.id ";

	public SubGeneroMusicalDAO() {
	}

	public SubGeneroMusicalDTO findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" WHERE ms.id = ? ");
			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			SubGeneroMusicalDTO sgm = null;
			while (rs.next()) {	
				sgm = loadNext(rs);
			}
			return sgm;	
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
		
	}

	public List<SubGeneroMusicalDTO> findByGeneroId(Connection c, Long generoId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" WHERE mg.id = ? ORDER BY ms.name ");
			
			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, generoId);
			rs = ps.executeQuery();

			
			List<SubGeneroMusicalDTO> resultados = new ArrayList<>();
			while (rs.next()) {	
				resultados.add(loadNext(rs));
			}
			return resultados;	
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	public List<SubGeneroMusicalDTO> findAll(Connection c) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" ORDER BY ms.name ");
			
			ps = c.prepareStatement(sql.toString());

			rs = ps.executeQuery();
			
			List<SubGeneroMusicalDTO> resultados = new ArrayList<>();
			while (rs.next()) {	
				resultados.add(loadNext(rs));
			}
			return resultados;	
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	private SubGeneroMusicalDTO loadNext(ResultSet rs) throws Exception {
		int i = 1; 
		SubGeneroMusicalDTO sgm = new SubGeneroMusicalDTO();
		sgm.setId(rs.getLong(i++));
		sgm.setNombre(rs.getString(i++));
		sgm.setGeneroMusicalId(rs.getLong(i++));
		sgm.setGeneroMusicalNombre(rs.getString(i++));
		return sgm;
	}
}

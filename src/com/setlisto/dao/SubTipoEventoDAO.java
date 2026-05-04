package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.SubTipoEventoDTO;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class SubTipoEventoDAO {
	
	private static String BASE_QUERY = "SELECT es.id, es.name, event_type_id, et.name FROM event_subtype es "
			+ " JOIN event_type et ON es.event_type_id = et.id ";

	public SubTipoEventoDAO() {
	}

	public SubTipoEventoDTO findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = JDBCUtils.getConnection();
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" WHERE es.id = ?");
			
			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();
			
			SubTipoEventoDTO ste = null;
			while (rs.next()) {
				ste = loadNext(rs);
			}
			return ste;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DAOUtils.close(rs, ps, c);
		}
	}
	
	public List<SubTipoEventoDTO> findByTipoEventoId(Long id) {
		List<SubTipoEventoDTO> resultados = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE et.id = ? ORDER BY es.name ASC");
			
			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();			

			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
			return resultados;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DAOUtils.close(rs, ps, c);
		}
	}


		
	private SubTipoEventoDTO loadNext(ResultSet rs) throws Exception {
		int i = 1; 
		SubTipoEventoDTO ste = new SubTipoEventoDTO();
		ste.setId(rs.getLong(i++));
		ste.setNombre(rs.getString(i++));
		ste.setTipoEventoId(rs.getLong(i++));
		ste.setTipoEventoNombre(rs.getString(i++));
		return ste;
	}
}

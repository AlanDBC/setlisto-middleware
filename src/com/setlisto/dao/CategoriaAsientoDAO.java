package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.CategoriaAsiento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class CategoriaAsientoDAO {

	public CategoriaAsientoDAO() {
	}
	
	private static final String BASE_QUERY = " SELECT id, name FROM seat_category ";

	public CategoriaAsiento findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE id = ? ");
			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();
			
			CategoriaAsiento ca = null;
			if (rs.next()) {
				ca = loadNext(rs);
			}
			return ca;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}


	public List<CategoriaAsiento> findAll() {
		List<CategoriaAsiento> resultados = new ArrayList<CategoriaAsiento>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			String sql = BASE_QUERY;
			ps = c.prepareStatement(sql);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
			return resultados;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}
	
	private CategoriaAsiento loadNext(ResultSet rs) throws Exception {
		int i = 1;
		CategoriaAsiento ca = new CategoriaAsiento();
		ca.setId(rs.getLong(i++));
		ca.setNombre(rs.getString(i++));
		return ca;
	}
}

package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.MetodoPago;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class MetodoPagoDAO {

	private static final String BASE_QUERY = "SELECT id, name FROM payment_method ";

	public MetodoPagoDAO() {
	}

	public MetodoPago findById(Long id) {
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

			MetodoPago mp = null; 
			if (rs.next()) {
				mp = loadNext(rs);				
			}
			return mp;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public List<MetodoPago> findAll() {
		List<MetodoPago> resultados = new ArrayList<MetodoPago>();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return resultados;
	}

	private MetodoPago loadNext(ResultSet rs) throws Exception {
		int i = 1;
		MetodoPago mp = new MetodoPago();
		mp.setId(rs.getLong(i++));
		mp.setNombre(rs.getString(i++));
		return mp;
	}
}

package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.MonedaPago;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class MonedaPagoDAO {

	private static final String BASE_QUERY = " SELECT id, code, symbol FROM payment_currency ";

	public MonedaPagoDAO() {
	}

	public MonedaPago findById(Long id) {
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

			MonedaPago mp = null;

			while (rs.next()) {
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

	public List<MonedaPago> findAll() {
		List<MonedaPago> resultados = new ArrayList<MonedaPago>();
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

	private MonedaPago loadNext(ResultSet rs) throws Exception {
		MonedaPago mp = new MonedaPago();
		int i = 1; 
		mp.setId(rs.getLong(i++));
		mp.setCodigo(rs.getString(i++));
		mp.setSimbolo(rs.getString(i++));
		return mp;
	}
}

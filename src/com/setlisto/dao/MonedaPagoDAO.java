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

	public MonedaPago findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<MonedaPago> findAll(Connection c) throws Exception {
		List<MonedaPago> resultados = new ArrayList<MonedaPago>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = BASE_QUERY;
			
			ps = c.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
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

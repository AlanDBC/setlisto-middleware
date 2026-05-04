package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.EstadoPago;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class EstadoPagoDAO {

	private static final String BASE_QUERY = " SELECT id, name FROM payment_status ";

	public EstadoPagoDAO() {
	}

	public EstadoPago findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append("WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			EstadoPago ep = null;
			while (rs.next()) {
				ep = loadNext(rs);
			}
			return ep;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public List<EstadoPago> findAll() {
		List<EstadoPago> resultados = new ArrayList<EstadoPago>();
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
	
	private EstadoPago loadNext(ResultSet rs) throws Exception {
		int i = 1;
		EstadoPago ep = new EstadoPago();
		ep.setId(rs.getLong(i++));
		ep.setNombre(rs.getString(i++));
		return ep;
	}
}

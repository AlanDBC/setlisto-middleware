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

	public EstadoPago findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<EstadoPago> findAll(Connection c) throws Exception {
		List<EstadoPago> resultados = new ArrayList<EstadoPago>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = BASE_QUERY;
			
			ps = c.prepareStatement(sql);
			rs = ps.executeQuery();

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
	
	private EstadoPago loadNext(ResultSet rs) throws Exception {
		int i = 1;
		EstadoPago ep = new EstadoPago();
		ep.setId(rs.getLong(i++));
		ep.setNombre(rs.getString(i++));
		return ep;
	}
}

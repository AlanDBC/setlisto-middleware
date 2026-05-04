package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.EstadoEvento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class EstadoEventoDAO {

	private static String BASE_QUERY = "SELECT id, name FROM event_status ";

	public EstadoEventoDAO() {
	}

	public EstadoEvento findById(Long id) {
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

			EstadoEvento estado = null; 
			while (rs.next()) {
				estado = loadNext(rs);
			}
			return estado;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public List<EstadoEvento> findAll() {
		List<EstadoEvento> resultados = new ArrayList<EstadoEvento>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			EstadoEvento estado = null;
			while (rs.next()) {
				estado = loadNext(rs);
				resultados.add(estado);
			}
			return resultados;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DAOUtils.close(rs, ps, c);
		}
		return resultados;
	}
	
	public EstadoEvento loadNext (ResultSet rs) throws Exception {
		int i = 1; 
		EstadoEvento estado = new EstadoEvento();
		estado.setId(rs.getLong(i++));
		estado.setNombre(rs.getString(i++));
		return estado;
	}
}
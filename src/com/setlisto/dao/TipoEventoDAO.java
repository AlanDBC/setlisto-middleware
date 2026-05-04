package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.TipoEvento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class TipoEventoDAO {

	private static String BASE_QUERY = "SELECT id, name FROM event_type";

	public TipoEventoDAO() {
	}

	public TipoEvento findById(Long id) {
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
			TipoEvento te = null;

			while (rs.next()) {
				te = loadNext(rs);
			}
			return te;
		}  catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public List<TipoEvento> findAll() {
		List<TipoEvento> resultados = new ArrayList<>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = JDBCUtils.getConnection();
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" ORDER BY name ");

			ps = c.prepareStatement(sql.toString());
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
		return resultados;
	}

	private TipoEvento loadNext(ResultSet rs) throws Exception {
		int i = 1;
		TipoEvento te = new TipoEvento();
		te.setId(rs.getLong(i++));
		te.setNombre(rs.getString(i++));
		return te;
	}
}

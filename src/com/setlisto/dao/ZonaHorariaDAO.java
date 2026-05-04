package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.ZonaHoraria;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class ZonaHorariaDAO {
	
	private static String BASE_QUERY = "SELECT id, name FROM time_zone ";

	public ZonaHorariaDAO() {
	}
	
	public ZonaHoraria findById(Long id) {
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

			ZonaHoraria zh = null; 
			while (rs.next()) {
				zh = loadNext(rs);
			}
			return zh;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}
	
	public List<ZonaHoraria> findAll() {
		List<ZonaHoraria> resultados = new ArrayList<ZonaHoraria>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			ZonaHoraria zh = null;
			while (rs.next()) {
				zh = loadNext(rs);
				resultados.add(zh);
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
	
	
	private ZonaHoraria loadNext(ResultSet rs) throws Exception {
		int i = 1;
		ZonaHoraria zona = new ZonaHoraria();
		zona.setId(rs.getLong(i++));
		zona.setNombre(rs.getString(i++));
		return zona;
	}
}

package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.Pais;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class PaisDAO {

	private static String BASE_QUERY = "SELECT id, name FROM country ";

	public PaisDAO() {
	}

	public Pais findById(Connection c, Long id)  throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			Pais pais = null;
			while (rs.next()) {
				pais = loadNext(rs);
			}
			return pais;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<Pais> findAll(Connection c) throws Exception {
		List<Pais> resultados = new ArrayList<Pais>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append( " ORDER BY name ");

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			Pais pais = null;
			while (rs.next()) {
				pais = loadNext(rs);
				resultados.add(pais);
			}
			return resultados;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	public Pais loadNext (ResultSet rs) throws Exception {
		int i = 1; 
		Pais pais = new Pais();
		pais.setId(rs.getLong(i++));
		pais.setNombre(rs.getString(i++));
		return pais;
	}
}
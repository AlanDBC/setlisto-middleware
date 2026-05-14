package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.GeneroMusical;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class GeneroMusicalDAO {

	private final String BASE_QUERY = " SELECT id, name FROM musical_genre ";

	public GeneroMusicalDAO() {
	}

	public GeneroMusical findById(Connection c, Long id) throws Exception {	
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" WHERE id = ?");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			GeneroMusical gm = null;
			if (rs.next()) {	
				gm = loadNext(rs);
			}
			return gm;	
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<GeneroMusical> findAll(Connection c) throws Exception {
		List<GeneroMusical> resultados = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(	"ORDER BY name");

			ps = c.prepareStatement(sql.toString());
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

	private GeneroMusical loadNext(ResultSet rs)  throws Exception {
		int i = 1;
		GeneroMusical gm = new GeneroMusical();
		gm.setId(rs.getLong(i++));
		gm.setNombre(rs.getString(i++));
		return gm;
	}
}

package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.CategoriaAsiento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class CategoriaAsientoDAO {

	private static final Logger logger = LogManager.getLogger(CategoriaAsientoDAO.class.getName());

	private static final String BASE_QUERY = " SELECT id, name FROM seat_category ";

	public CategoriaAsiento findById(Connection c,Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE id = ? ");
			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			CategoriaAsiento ca = null;
			if (rs.next()) {
				ca = loadNext(rs);
			}
			return ca;
		} catch (SQLException e) {
			logger.error("Error en CategoriaAsientoDAO.findById con ID {}: {}", id, e.getMessage());
			throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}


	public List<CategoriaAsiento> findAll(Connection c) throws DataException {
		List<CategoriaAsiento> resultados = new ArrayList<CategoriaAsiento>();
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
		} catch (SQLException e) {
			logger.error("Error en CategoriaAsientoDAO.findAll: No se pudo recuperar el listado de categorías. Detalle: {}", e.getMessage());
			throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	private CategoriaAsiento loadNext(ResultSet rs) throws SQLException {
		int i = 1;
		CategoriaAsiento ca = new CategoriaAsiento();
		ca.setId(rs.getLong(i++));
		ca.setNombre(rs.getString(i++));
		return ca;
	}
}

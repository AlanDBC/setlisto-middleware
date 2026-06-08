package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.DataException;
import com.setlisto.model.TipoEvento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class TipoEventoDAO {

	private static Logger logger = LogManager.getLogger(TipoEventoDAO.class.getName());

	private static String BASE_QUERY = "SELECT id, name FROM event_type";

	public TipoEventoDAO() {
	}

	public TipoEvento findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
		}  catch (SQLException e) {
			logger.error("Error en TipoEventoDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<TipoEvento> findAll(Connection c) throws DataException {
		List<TipoEvento> resultados = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY); 
			sql.append(" ORDER BY name ");

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
			return resultados;
		} catch (SQLException e) {
			logger.error("Error en TipoEventoDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	private TipoEvento loadNext(ResultSet rs) throws SQLException {
		int i = 1;
		TipoEvento te = new TipoEvento();
		te.setId(rs.getLong(i++));
		te.setNombre(rs.getString(i++));
		return te;
	}
}

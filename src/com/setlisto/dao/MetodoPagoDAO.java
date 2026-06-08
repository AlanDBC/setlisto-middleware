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
import com.setlisto.model.MetodoPago;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class MetodoPagoDAO {
	
	private static Logger logger = LogManager.getLogger(MetodoPagoDAO.class.getName());

	private static final String BASE_QUERY = "SELECT id, name FROM payment_method ";

	public MetodoPagoDAO() {
	}

	public MetodoPago findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			MetodoPago mp = null; 
			if (rs.next()) {
				mp = loadNext(rs);				
			}
			return mp;
		} catch (SQLException e) {
			logger.error("Error en MetodoPagoDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<MetodoPago> findAll(Connection c) throws DataException {
		List<MetodoPago> resultados = new ArrayList<MetodoPago>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = BASE_QUERY;

			ps = c.prepareStatement(sql);
			rs = ps.executeQuery();

			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
		} catch (SQLException e) {
			logger.error("Error en MetodoPagoDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return resultados;
	}

	private MetodoPago loadNext(ResultSet rs) throws SQLException {
		int i = 1;
		MetodoPago mp = new MetodoPago();
		mp.setId(rs.getLong(i++));
		mp.setNombre(rs.getString(i++));
		return mp;
	}
}

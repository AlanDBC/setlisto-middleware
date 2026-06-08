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
import com.setlisto.model.MonedaPago;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class MonedaPagoDAO {

	private static Logger logger = LogManager.getLogger(MonedaPagoDAO.class.getName());

	private static final String BASE_QUERY = " SELECT id, code, symbol FROM payment_currency ";

	public MonedaPagoDAO() {
	}

	public MonedaPago findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			MonedaPago mp = null;

			while (rs.next()) {
				mp = loadNext(rs);
			}
			return mp;
		} catch (SQLException e) {
			logger.error("Error en MonedaPagoDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<MonedaPago> findAll(Connection c) throws DataException {
		List<MonedaPago> resultados = new ArrayList<MonedaPago>();
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
			logger.error("Error en MonedaPagoDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return resultados;
	}

	private MonedaPago loadNext(ResultSet rs) throws SQLException {
		MonedaPago mp = new MonedaPago();
		int i = 1; 
		mp.setId(rs.getLong(i++));
		mp.setCodigo(rs.getString(i++));
		mp.setSimbolo(rs.getString(i++));
		return mp;
	}
}

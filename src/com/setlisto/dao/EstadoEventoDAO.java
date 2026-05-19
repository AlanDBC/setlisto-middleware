package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.EstadoEvento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class EstadoEventoDAO {
	
	private static Logger logger = LogManager.getLogger(EstadoEventoDAO.class.getName());

	private static String BASE_QUERY = "SELECT id, name FROM event_status ";

	public EstadoEventoDAO() {
	}

	public EstadoEvento findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
		} catch (SQLException e) {
			logger.error("Error en EstadoEventoDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		}
		finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<EstadoEvento> findAll(Connection c) throws DataException {
		List<EstadoEvento> resultados = new ArrayList<EstadoEvento>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			EstadoEvento estado = null;
			while (rs.next()) {
				estado = loadNext(rs);
				resultados.add(estado);
			}
			return resultados;
		} catch (SQLException e) {
			logger.error("Error en EstadoEventoDAO.findAll: {}", e.getMessage());
		    throw new DataException(e);
		}
		finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	public EstadoEvento loadNext (ResultSet rs) throws SQLException {
		int i = 1; 
		EstadoEvento estado = new EstadoEvento();
		estado.setId(rs.getLong(i++));
		estado.setNombre(rs.getString(i++));
		return estado;
	}
}
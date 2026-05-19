package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.ZonaHoraria;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class ZonaHorariaDAO {
	
	private static Logger logger = LogManager.getLogger(ZonaHorariaDAO.class.getName());

	private static String BASE_QUERY = "SELECT id, name FROM time_zone ";

	public ZonaHorariaDAO() {
	}
	
	public ZonaHoraria findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
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
		} catch (SQLException e) {
			logger.error("Error en ZonaHorariaDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		}
		finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	public List<ZonaHoraria> findAll(Connection c)  throws DataException{
		List<ZonaHoraria> resultados = new ArrayList<ZonaHoraria>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			ZonaHoraria zh = null;
			while (rs.next()) {
				zh = loadNext(rs);
				resultados.add(zh);
			}
			return resultados;
		} catch (SQLException e) {
			logger.error("Error en ZonaHorariaDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
		}
		finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	
	private ZonaHoraria loadNext(ResultSet rs) throws SQLException {
		int i = 1;
		ZonaHoraria zona = new ZonaHoraria();
		zona.setId(rs.getLong(i++));
		zona.setNombre(rs.getString(i++));
		return zona;
	}
}

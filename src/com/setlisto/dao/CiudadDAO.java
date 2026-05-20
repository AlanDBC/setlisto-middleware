package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.Ciudad;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class CiudadDAO {
	
	private static final Logger logger = LogManager.getLogger(CiudadDAO.class.getName());

	public List<Ciudad> findByRegionId(Connection c, Long regionId) throws DataException {
		List<Ciudad> resultados = new ArrayList<Ciudad>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id, name, region_id FROM city WHERE region_id = ? ORDER BY name ASC";
			
			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, regionId);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
			return resultados;
		} catch (SQLException e) {
			logger.error("Error buscando por region con id {}: {}", regionId, e.getMessage());
			throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}	
	}

	private static Ciudad loadNext(ResultSet rs) throws SQLException {
		int i = 1;
		Ciudad ciudad = new Ciudad();
		ciudad.setId(rs.getLong(i++));
		ciudad.setNombre(rs.getString(i++));
		ciudad.setRegionId(rs.getLong(i++));
		return ciudad;
	}
}

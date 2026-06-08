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
import com.setlisto.model.Region;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class RegionDAO {
	
	private static Logger logger = LogManager.getLogger(RegionDAO.class.getName());

	public RegionDAO() {
	}
	
	public List<Region> findByPaisId(Connection c, Long id)  throws DataException{
		List<Region> resultados = new ArrayList<Region>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "SELECT id, name, country_id FROM region WHERE country_id = ? ORDER BY name ASC";
			
			ps = c.prepareStatement(sql);			
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				int i = 1;
				Region region = new Region();
				region.setId(rs.getLong(i++));
				region.setNombre(rs.getString(i++));
				region.setPaisId(rs.getLong(i++));
				resultados.add(region);
			}
			return resultados;
		} catch (SQLException e) {
			logger.error("Error en RegionDAO.findByPaisId con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}
}
package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.Region;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class RegionDAO {

	public RegionDAO() {
	}
	
	public List<Region> findByPaisId(Long id) {
		List<Region> resultados = new ArrayList<Region>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();
			
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return resultados;
	}
} // class
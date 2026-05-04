package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.Ciudad;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class CiudadDAO {

	public CiudadDAO() {
	}

	public List<Ciudad> findByRegionId(Long regionId) {
		List<Ciudad> resultados = new ArrayList<Ciudad>();
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = JDBCUtils.getConnection();

			String sql = "SELECT id, name, region_id FROM city WHERE region_id = ? ORDER BY name ASC";

			ps = c.prepareStatement(sql);

			DAOUtils.setParameters(ps, regionId);

			rs = ps.executeQuery();

			while (rs.next()) {
				resultados.add(loadNext(rs));
			}
			return resultados;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return resultados;	
	}

	private static Ciudad loadNext(ResultSet rs) throws Exception {
		int i = 1;
		Ciudad ciudad = new Ciudad();
		ciudad.setId(rs.getLong(i++));
		ciudad.setNombre(rs.getString(i++));
		ciudad.setRegionId(rs.getLong(i++));
		return ciudad;
	}
}

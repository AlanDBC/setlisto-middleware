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

	public List<Ciudad> findByRegionId(Connection c, Long regionId) throws Exception {
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
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}	
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

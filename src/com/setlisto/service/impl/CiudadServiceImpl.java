package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.CiudadDAO;
import com.setlisto.model.Ciudad;
import com.setlisto.service.CiudadService;
import com.setlisto.utils.JDBCUtils;

public class CiudadServiceImpl implements CiudadService {

	private static final Logger logger = LogManager.getLogger(CiudadServiceImpl.class.getName());
	private CiudadDAO ciudadDAO = null;

	public CiudadServiceImpl() {
		this.ciudadDAO = new CiudadDAO();
	}

	/**
	 * Delega la búsqueda de ciudades por región al DAO correspondiente.
	 */
	@Override
	public List<Ciudad> findByRegionId(Long regionId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Ciudad> ciudades = ciudadDAO.findByRegionId(c, regionId);
			commit = true;
			return ciudades;
		} catch (Exception e) {
			logger.error("Buscando por region id {}: {}",regionId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
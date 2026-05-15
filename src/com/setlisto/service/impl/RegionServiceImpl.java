package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.RegionDAO;
import com.setlisto.model.Region;
import com.setlisto.service.RegionService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación de la lógica de negocio para regiones.
 */
public class RegionServiceImpl implements RegionService {

	private static final Logger logger = LogManager.getLogger(RegionServiceImpl.class.getName());

	private RegionDAO regionDAO = null;

	public RegionServiceImpl() {
		this.regionDAO = new RegionDAO();
	}

	@Override
	public List<Region> findByPaisId(Long countryId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Region> regiones = regionDAO.findByPaisId(c, countryId);
			commit = true;
			return regiones;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", countryId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
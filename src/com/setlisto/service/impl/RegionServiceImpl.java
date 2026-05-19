package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.DataException;
import com.setlisto.dao.RegionDAO;
import com.setlisto.model.Region;
import com.setlisto.service.RegionService;
import com.setlisto.service.ServiceException;
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
	public List<Region> findByPaisId(Long countryId) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<Region> regiones = regionDAO.findByPaisId(c, countryId);
			commit = true;
			return regiones;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar por pais con id {}: {}", countryId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", countryId, e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
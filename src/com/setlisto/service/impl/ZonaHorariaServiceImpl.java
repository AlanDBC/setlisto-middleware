package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.ZonaHorariaDAO;
import com.setlisto.model.ZonaHoraria;
import com.setlisto.service.ZonaHorariaService;
import com.setlisto.utils.JDBCUtils;

public class ZonaHorariaServiceImpl implements ZonaHorariaService {

	private static final Logger logger = LogManager.getLogger(ZonaHorariaServiceImpl.class.getName());
	private ZonaHorariaDAO zonaHorariaDAO = null;

	public ZonaHorariaServiceImpl() {
		this.zonaHorariaDAO = new ZonaHorariaDAO();
	}

	@Override
	public ZonaHoraria findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			ZonaHoraria zona = zonaHorariaDAO.findById(c, id);
			commit = true;
			return zona;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<ZonaHoraria> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<ZonaHoraria> zonas = zonaHorariaDAO.findAll(c);
			commit = true;
			return zonas;
		} catch (Exception e) {
			logger.error("Buscando todas: {}", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}

	}
}

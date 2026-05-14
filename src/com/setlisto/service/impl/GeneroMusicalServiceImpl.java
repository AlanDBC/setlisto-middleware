package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.GeneroMusicalDAO;
import com.setlisto.model.GeneroMusical;
import com.setlisto.service.GeneroMusicalService;
import com.setlisto.utils.JDBCUtils;

public class GeneroMusicalServiceImpl implements GeneroMusicalService {

	private static final Logger logger = LogManager.getLogger(GeneroMusicalServiceImpl.class.getName());

	private GeneroMusicalDAO generoMusicalDAO = null;

	public GeneroMusicalServiceImpl() {
		this.generoMusicalDAO = new GeneroMusicalDAO();
	}

	@Override
	public GeneroMusical findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			GeneroMusical genero = generoMusicalDAO.findById(c, id);
			commit = true;
			return genero;
		} catch (Exception e) {
			logger.error("Buscando genero por id{}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<GeneroMusical> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<GeneroMusical> generos = generoMusicalDAO.findAll(c);
			commit = true;
			return generos;
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
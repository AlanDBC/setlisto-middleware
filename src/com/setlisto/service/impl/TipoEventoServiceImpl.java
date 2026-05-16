package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.TipoEventoDAO;
import com.setlisto.model.TipoEvento;
import com.setlisto.service.TipoEventoService;
import com.setlisto.utils.JDBCUtils;

public class TipoEventoServiceImpl implements TipoEventoService {

	private static final Logger logger = LogManager.getLogger(TipoEventoServiceImpl.class.getName());
	private TipoEventoDAO tipoEventoDAO = null;

	public TipoEventoServiceImpl() {
		this.tipoEventoDAO = new TipoEventoDAO();
	}

	@Override
	public TipoEvento findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			TipoEvento tipo = tipoEventoDAO.findById(c, id);
			commit = true;
			return tipo;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<TipoEvento> findAll() throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<TipoEvento> tipos = tipoEventoDAO.findAll(c);
			commit = true;
			return tipos;
		} catch (Exception e) {
			logger.error("Buscando todos: {}", e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.CategoriaAsientoDAO;
import com.setlisto.dao.DataException;
import com.setlisto.model.CategoriaAsiento;
import com.setlisto.service.CategoriaAsientoService;
import com.setlisto.service.ServiceException;
import com.setlisto.utils.JDBCUtils;

public class CategoriaAsientoServiceImpl implements CategoriaAsientoService {

	private static final Logger logger = LogManager.getLogger(CategoriaAsientoServiceImpl.class.getName());
	private CategoriaAsientoDAO categoriaAsientoDAO = null;

	public CategoriaAsientoServiceImpl() throws Exception {
		this.categoriaAsientoDAO = new CategoriaAsientoDAO();
	}

	@Override
	public CategoriaAsiento findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			CategoriaAsiento categoria = categoriaAsientoDAO.findById(c, id);
			commit = true;		
			return categoria;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar categoria con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw new ServiceException(e);		
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<CategoriaAsiento> findAll() throws ServiceException {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<CategoriaAsiento> categorias = categoriaAsientoDAO.findAll(c);
			commit = true;
			return categorias;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar todas las categorias: {}", e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando todos", e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
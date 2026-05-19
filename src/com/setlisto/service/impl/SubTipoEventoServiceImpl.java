package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.dao.DataException;
import com.setlisto.dao.SubTipoEventoDAO;
import com.setlisto.model.SubTipoEventoDTO;
import com.setlisto.service.ServiceException;
import com.setlisto.service.SubTipoEventoService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación del servicio de subtipos de eventos.
 */
public class SubTipoEventoServiceImpl implements SubTipoEventoService {

	private static final Logger logger = LogManager.getLogger(SubTipoEventoServiceImpl.class.getName());

	private SubTipoEventoDAO subTipoEventoDAO = null;

	public SubTipoEventoServiceImpl() {
		this.subTipoEventoDAO = new SubTipoEventoDAO();
	}

	@Override
	public SubTipoEventoDTO findById(Long id) throws ServiceException {
		Connection c = null;
		boolean commit = false;   
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			SubTipoEventoDTO subtipo = subTipoEventoDAO.findById(c, id);
			commit = true;
			return subtipo;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar subtipo de evento con id {}: {}", id, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}",id , e.getMessage());
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<SubTipoEventoDTO> findByTipoEvento(Long tipoEventoId) throws ServiceException {
		Connection c = null;
		boolean commit = false;   
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<SubTipoEventoDTO> subtipos = subTipoEventoDAO.findByTipoEventoId(c, tipoEventoId);
			commit = true;
			return subtipos;
		} catch (DataException e) {
			logger.error("Error de persistencia al buscar subtipo por tipo de evento con id {}: {}", tipoEventoId, e.getMessage());
			throw new ServiceException(e);
		} catch (Exception e) {
			logger.error("Buscando por tipo de evento con id {}: {}",tipoEventoId, e.getMessage());
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
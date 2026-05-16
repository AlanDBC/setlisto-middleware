package com.setlisto.service.impl;

import java.sql.Connection;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.dao.TicketDAO;
import com.setlisto.model.Results;
import com.setlisto.model.TicketDTO;
import com.setlisto.service.TicketService;
import com.setlisto.utils.JDBCUtils;

/**
 * Implementación de la lógica de negocio para la gestión de tickets.
 */
public class TicketServiceImpl implements TicketService {

	private static final Logger logger = LogManager.getLogger(TicketServiceImpl.class.getName());

	private TicketDAO ticketDAO = null;

	public TicketServiceImpl() {
		this.ticketDAO = new TicketDAO();
	}

	@Override
	public TicketDTO findById(Long id) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			TicketDTO ticket = ticketDAO.findById(c, id);
			commit = true;
			return ticket;
		} catch (Exception e) {
			logger.error("Buscando por id {}: {}", id, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public TicketDTO findByCode(String code) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			TicketDTO ticket = ticketDAO.findByCode(c, code);
			commit = true;
			return ticket;
		} catch (Exception e) {
			logger.error("Buscando por codigo de ticket {}: {}", code, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<TicketDTO> findByPaymentId(Long paymentId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<TicketDTO> tickets = ticketDAO.findByPaymentId(c, paymentId);
			commit = true;
			return tickets;
		} catch (Exception e) {
			logger.error("Buscando por id de pago {}: {}", paymentId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<TicketDTO> findByClienteId(Long clienteId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<TicketDTO> tickets = ticketDAO.findByClienteId(c, clienteId);
			commit = true;
			return tickets;
		} catch (Exception e) {
			logger.error("Buscando por cliente con id {}: {}", clienteId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public List<TicketDTO> findByEventoId(Long musicalEventId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			List<TicketDTO> tickets = ticketDAO.findByEventoId(c, musicalEventId);
			commit = true;
			return tickets;
		} catch (Exception e) {
			logger.error("Buscando por evento con id {}: {}", musicalEventId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public TicketDTO create(TicketDTO ticket) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Long generatedId = ticketDAO.create(c, ticket);
			if (generatedId != null) {
				ticket.setId(generatedId);
				return ticket;
			}
			commit = true;
			return ticket;
		} catch (Exception e) {
			logger.error("Creando ticket {}: {}", ticket, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public Results<TicketDTO> findByCriteria(TicketCriteria criteria, int from, int pageSize) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			Results<TicketDTO> resultados = ticketDAO.findByCriteria(c, criteria, from, pageSize);
			commit = true;
			return resultados;
		} catch (Exception e) {
			logger.error("Buscando por evento con criteria {}: {}", criteria, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public boolean existsBySeatOfEventId(Long seatOfMusicalEventId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			boolean existe = ticketDAO.existsBySeatOfEventId(c, seatOfMusicalEventId);
			commit = true;
			return existe;
		} catch (Exception e) {
			logger.error("Buscando ticket existente para plaza del evento con id {}: {}", seatOfMusicalEventId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}

	@Override
	public long countByEventoId(Long musicalEventId) throws Exception {
		Connection c = null;
		boolean commit = false;
		try {
			c = JDBCUtils.getConnection();
			c.setAutoCommit(false);
			long total = ticketDAO.countByEventoId(c, musicalEventId);
			commit = true;
			return total;
		} catch (Exception e) {
			logger.error("Buscando total de tickets para evento con id {}: {}", musicalEventId, e.getMessage(), e);
			throw e;
		} finally {
			JDBCUtils.close(c, commit);
		}
	}
}
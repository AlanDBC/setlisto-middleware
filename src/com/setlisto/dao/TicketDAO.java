package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.TicketCriteria;
import com.setlisto.model.Results;
import com.setlisto.model.Ticket;
import com.setlisto.model.TicketDTO;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

public class TicketDAO {
	
	private static Logger logger = LogManager.getLogger(TicketDAO.class.getName()); // TODO

	private static String BASE_QUERY = " SELECT t.id, t.code, t.price, t.purchase_date, t.payment_id, c.id, c.name, me.id, me.name, me.start_date, ste.id, "
			+ " ste.name, ste.address, stme.id, st.row, st.number, COALESCE(stc.id, ezc.id), COALESCE(stc.name, ezc.name), "
			+ " sts.id, sts.name, tty.id, tty.name, ez.id, ez.section_name, ez.available_capacity "
			+ " FROM ticket t"
			+ " INNER JOIN ticket_type tty ON tty.id = t.ticket_type_id "
			+ " INNER JOIN payment pmt ON pmt.id = t.payment_id "
			+ " INNER JOIN customer c ON c.id = pmt.customer_id "
			+ " LEFT JOIN seat_of_musical_event stme ON stme.id = t.seat_of_musical_event_id "
			+ " LEFT JOIN event_zone ez ON ez.id = t.event_zone_id "
			+ " INNER JOIN musical_event me ON me.id = COALESCE(stme.musical_event_id, ez.musical_event_id) "
			+ " LEFT JOIN seat st ON st.id = stme.seat_id "
			+ " LEFT JOIN seat_category stc ON stc.id = st.seat_category_id "
			+ " LEFT JOIN seat_category ezc ON ezc.id = ez.seat_category_id "
			+ " LEFT JOIN seat_status sts ON sts.id = stme.seat_status_id "
			+ " INNER JOIN site ste ON ste.id = me.site_id ";

	public TicketDAO() {
	}

	public Long create(Connection c, Ticket ticket) throws Exception {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        String sql = "INSERT INTO ticket (code, price, purchase_date, payment_id, " +
	                     "seat_of_musical_event_id, ticket_type_id, event_zone_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

	        ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

	        DAOUtils.setParameters(ps,
	            ticket.getCodigo(),
	            ticket.getPrecio(),
	            Timestamp.valueOf(ticket.getFechaCompra()),
	            ticket.getPagoId(),
	            ticket.getPlazaEventoMusicalId(),
	            ticket.getTipoTicketId(),
	            ticket.getEventZoneId()
	        );

	        int rows = ps.executeUpdate();

	        if (rows > 0) {
	            rs = ps.getGeneratedKeys();
	            if (rs.next()) {
	                return rs.getLong(1);
	            }
	        }
	    } catch (Exception e) {
	    	throw e;
	    } finally {
	    	JDBCUtils.close(rs, ps);
	    }
	    return null;
	}

	public TicketDTO findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE t.id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			TicketDTO tck = null;
			if (rs.next()) {
				tck = loadNext(rs);
			}
			return tck;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public TicketDTO findByCode(Connection c, String code) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE t.code = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, code);
			rs = ps.executeQuery();

			TicketDTO tck = null;
			if (rs.next()) {
				tck = loadNext(rs);
			}
			return tck;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<TicketDTO> findByPaymentId(Connection c, Long paymentId) throws Exception {
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE t.payment_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, paymentId);
			rs = ps.executeQuery();

			while (rs.next()) {
				tickets.add(loadNext(rs));
			}
			return tickets;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<TicketDTO> findByClienteId(Connection c, Long clienteId) throws Exception{
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE c.id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, clienteId);
			rs = ps.executeQuery();

			while (rs.next()) {
				tickets.add(loadNext(rs));
			}
			return tickets;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<TicketDTO> findByEventoId(Connection c, Long musicalEventId) throws Exception{ 
		List<TicketDTO> tickets = new ArrayList<TicketDTO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE me.id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, musicalEventId);
			rs = ps.executeQuery();

			while (rs.next()) {
				tickets.add(loadNext(rs));
			}
			return tickets;
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public boolean existsBySeatOfEventId(Connection c, Long seatOfMusicalEventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT 1 FROM ticket WHERE seat_of_musical_event_id = ? ");
			
			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, seatOfMusicalEventId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return false;
	}

	public long countByEventoId(Connection c, Long musicalEventId) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) FROM ticket t ");
		sql.append(" LEFT JOIN seat_of_musical_event stme ON stme.id = t.seat_of_musical_event_id ");
		sql.append(" LEFT JOIN event_zone ez ON ez.id = t.event_zone_id ");
		sql.append(" WHERE COALESCE(stme.musical_event_id, ez.musical_event_id) = ? ");
			
			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, musicalEventId);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return 0;
	}

	public Results<TicketDTO> findByCriteria(Connection c, TicketCriteria criteria, int from, int pageSize) throws Exception {
		logger.info("Criteria: {}", criteria); 
	    
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    Results<TicketDTO> results = new Results<TicketDTO>();
	    try {
	        StringBuilder sql = new StringBuilder(BASE_QUERY);
	        
	        List<String> condiciones = new ArrayList<>();
	        List<Object> parametros = new ArrayList<>();

	        SQLUtils.addClause(criteria.getClienteId(), condiciones, " c.id = ? ", parametros, criteria.getClienteId());
	        SQLUtils.addClause(criteria.getEventoId(), condiciones, " me.id = ? ", parametros, criteria.getEventoId());
	        SQLUtils.addClause(criteria.getPagoId(), condiciones, " pmt.id = ? ", parametros, criteria.getPagoId());
	        SQLUtils.addClause(criteria.getDesde(), condiciones, " t.purchase_date >= ? ", parametros, criteria.getDesde());
	        SQLUtils.addClause(criteria.getHasta(), condiciones, " t.purchase_date <= ? ", parametros, criteria.getHasta());

	        if (!condiciones.isEmpty()) {
	            sql.append(" WHERE ");
	            sql.append(String.join(" AND ", condiciones));
	        }
	        
	        sql.append(" ORDER BY ");
			sql.append(criteria.getOrderBy());
			sql.append(criteria.getAscDesc() ? " ASC " : " DESC "); 
			
			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}: {}:", criteria, sql);
			} 
			
			ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        
			DAOUtils.setParameters(ps, parametros);

	        rs = ps.executeQuery();
	        List<TicketDTO> resultsPage = new ArrayList<TicketDTO>();

	        if (from>=1) {
				int count = 0;
				rs.absolute(from);
				do {
					resultsPage.add(loadNext(rs));
					count++;
				} while (count<pageSize && rs.next()); 	// Mientras no se alcance el pageSize y haya más registros, se siguen cargando resultados
			}
			int totalResults = SQLUtils.getTotalRows(rs);
			
			results.setPage(resultsPage); // Se setea la página de resultados (subconjunto de resultados)
			results.setTotal(totalResults);

	        return results;
	    } catch (Exception e) {
	    	throw e;
	    } finally {
	    	JDBCUtils.close(rs, ps);
	    }
	}

	private TicketDTO loadNext(ResultSet rs) throws Exception {
		int i = 1;
		TicketDTO tck = new TicketDTO();
		tck = new TicketDTO();
		tck.setId(rs.getLong(i++));
		tck.setCodigo(rs.getString(i++));
		tck.setPrecio(rs.getBigDecimal(i++));
		tck.setFechaCompra(rs.getTimestamp(i++).toLocalDateTime());
		tck.setPagoId(rs.getLong(i++));
		tck.setClienteId(rs.getLong(i++));
		tck.setClienteNombre(rs.getString(i++));
		tck.setEventoId(rs.getLong(i++));
		tck.setEventoNombre(rs.getString(i++));
		tck.setEventoFecha(rs.getTimestamp(i++).toLocalDateTime());
		tck.setLugarId(rs.getLong(i++));
		tck.setLugarNombre(rs.getString(i++));
		tck.setLugarDireccion(rs.getString(i++));
		tck.setPlazaEventoMusicalId(rs.getLong(i++));
		tck.setPlazaFila(rs.getString(i++));
		tck.setPlazaNumero(rs.getLong(i++));
		tck.setPlazaCategoriaId(rs.getLong(i++));
		tck.setPlazaCategoriaNombre(rs.getString(i++));
		rs.getLong(i++);
		i++;
		tck.setTipoTicketId(rs.getLong(i++));
		tck.setTipoTicketNombre(rs.getString(i++));
		Long eventZoneId = rs.getLong(i++);
		tck.setEventZoneId(rs.wasNull() ? null : eventZoneId);
		tck.setEventZoneSectionName(rs.getString(i++));
		int available = rs.getInt(i++);
		tck.setEventZoneAvailableCapacity(rs.wasNull() ? null : available);
		return tck;
	}
}

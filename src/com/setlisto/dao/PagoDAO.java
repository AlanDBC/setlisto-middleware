package com.setlisto.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.PagoCriteria;
import com.setlisto.model.Pago;
import com.setlisto.model.PagoDTO;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

public class PagoDAO {
	
	private static Logger logger = LogManager.getLogger(PagoDAO.class.getName());

	private static String BASE_QUERY = " SELECT p.id, p.amount, pm.id, pm.name, pc.id, pc.symbol, pc.code, "
			+ " p.transaction_code, p.created_at, p.payment_date, c.id, c.name, ps.id, ps.name "
			+ " FROM payment p "
			+ " JOIN customer c ON c.id = p.customer_id "
			+ " JOIN payment_status ps ON ps.id = p.payment_status_id "
			+ " JOIN payment_currency pc ON pc.id = p.payment_currency_id "
			+ " JOIN payment_method pm ON pm.id = p.payment_method_id ";

	public PagoDAO() {
	}

	public PagoDTO findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE p.id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			PagoDTO pago = null;
			if (rs.next()) {
				pago = loadNext(rs);
			}
			return pago;			
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	public Pago create(Connection c, Pago pago) throws DataException {
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    try {
	        // 1. Verificación de reglas de negocio (Código de transacción único)
	        if (existsByReference(c,pago.getCodigoTransaccion())) {
	            return null;
	        }
	        StringBuilder sql = new StringBuilder();
	        sql.append(" INSERT INTO payment (amount, transaction_code, payment_date, ");
	        sql.append(" customer_id, payment_status_id, payment_method_id, payment_currency_id) ");
	        sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?) ");
	        
	        ps = c.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS); 

	        DAOUtils.setParameters(ps,
	            pago.getMonto(),
	            pago.getCodigoTransaccion(),
	            pago.getFechaPago(),
	            pago.getClienteId(),
	            pago.getEstadoPagoId(),
	            pago.getMetodoPagoId(),
	            pago.getMonedaId()
	        );

	        int rows = ps.executeUpdate(); 
	        if (rows > 0) {
	            rs = ps.getGeneratedKeys(); 
	            if (rs.next()) {
	                pago.setId(rs.getLong(1));
	            }
	            return pago; // Devolvemos el objeto completo enriquecido
	        }
	    } catch (SQLException e) {
	    	logger.error("Error en PagoDAO.create con pago {}: {}", pago, e.getMessage());
		    throw new DataException(e); 
	    } finally {
	    	JDBCUtils.close(rs, ps); 
	    }
	    return null;
	}

	public List<PagoDTO> findAll(Connection c) throws DataException {
		List<PagoDTO> pagos = new ArrayList<PagoDTO>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" ORDER BY p.id ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps);
			rs = ps.executeQuery();

			while (rs.next()) {
				pagos.add(loadNext(rs));
			}
			return pagos;			
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public BigDecimal getTotalPagadoByClienteId(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COALESCE(SUM(p.amount), 0) ");
			sql.append(" FROM payment p ");
			sql.append(" WHERE p.customer_id = ? ");
			sql.append(" AND p.payment_status_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id, 2); // el ID 2 corresponde a 'APPROVED'
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getBigDecimal(1);
			}
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.getTotalPagadoByClienteId con Id {}: {}", id, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return null;
	}

	public PagoDTO findUltimoPagoByClienteId(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE p.customer_id = ? ");
			sql.append(" ORDER BY p.created_at DESC ");
			sql.append(" LIMIT 1 ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			PagoDTO pago = null;
			if (rs.next()) {
				pago = loadNext(rs);
			}
			return pago;			
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.findUltimoPagoByClienteId con Id {}: {}", id, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public boolean updateStatus(Connection c, Long paymentId, Long statusId) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE payment SET payment_status_id = ? WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, statusId, paymentId);

			int rows = ps.executeUpdate();

			return rows > 0;
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.updateStatus con pago id {} y estado id {}: {}", paymentId, statusId, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}	
	}

	public boolean existsByReference(Connection c, String transactionCode) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT COUNT(*) ");
			sql.append(" FROM payment p ");
			sql.append(" WHERE p.transaction_code = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, transactionCode);
			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.existsByReference con codigo de transaccion {}: {}", transactionCode, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return false;
	}

	public PagoDTO findByCodigoDeTransaccion(Connection c, String transactionCode) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE p.transaction_code = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, transactionCode);
			rs = ps.executeQuery();

			PagoDTO pago = null;
			if (rs.next()) {
				pago = loadNext(rs);
			}
			return pago;

		} catch (SQLException e) {
			logger.error("Error en PagoDAO.findByCodigoDeTransaccion con codigo de transaccion {}: {}", transactionCode, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Results<PagoDTO> findByCriteria(Connection c, PagoCriteria criteria, int from, int pageSize) throws DataException {
		logger.info("Criteria: {}", criteria);
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Results<PagoDTO> results = new Results<PagoDTO>();
		
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<String>();
			List<Object> parametros = new ArrayList<Object>();

			SQLUtils.addClause(criteria.getClienteId(), condiciones, " c.id = ? ", parametros, criteria.getClienteId());
			SQLUtils.addClause(criteria.getMonedaId(), condiciones, " pc.id = ? ", parametros, criteria.getMonedaId());
			SQLUtils.addClause(criteria.getEstadoPagoId(), condiciones, " ps.id = ? ", parametros, criteria.getEstadoPagoId());
			SQLUtils.addClause(criteria.getMetodoPagoId(), condiciones, " pm.id = ? ", parametros, criteria.getMetodoPagoId());
			SQLUtils.addClause(criteria.getFechaDesde(), condiciones, " p.created_at >= ? " , parametros, criteria.getFechaDesde());
			SQLUtils.addClause(criteria.getFechaHasta(), condiciones, " p.created_at <= ? " , parametros, criteria.getFechaHasta());

			if (!condiciones.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(String.join(" AND ", condiciones) );
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

			List<PagoDTO> resultsPage = new ArrayList<PagoDTO>();
			
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
		} catch (SQLException e) {
			logger.error("Error en PagoDAO.findByCriteria con criteria {}: {}", criteria, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	private PagoDTO loadNext (ResultSet rs) throws SQLException {
		PagoDTO pago = new PagoDTO();
		int i = 1;
		pago = new PagoDTO();
		pago.setId(rs.getLong(i++));
		pago.setMonto(rs.getBigDecimal(i++));
		pago.setMetodoPagoId(rs.getLong(i++));
		pago.setMetodoPagoNombre(rs.getString(i++));
		pago.setMonedaId(rs.getLong(i++));
		pago.setMonedaSimbolo(rs.getString(i++));
		pago.setMonedaCodigo(rs.getString(i++));
		pago.setCodigoTransaccion(rs.getString(i++));
		pago.setFechaCreacion(rs.getTimestamp(i++).toLocalDateTime());
		Timestamp ts = rs.getTimestamp(i++);
		pago.setFechaPago(ts != null ? ts.toLocalDateTime() : null);
		pago.setClienteId(rs.getLong(i++));
		pago.setClienteNombre(rs.getString(i++));
		pago.setEstadoPagoId(rs.getLong(i++));
		pago.setEstadoPagoNombre(rs.getString(i++));
		return pago;
	}
}

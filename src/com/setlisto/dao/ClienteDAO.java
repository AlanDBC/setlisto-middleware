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

import com.setlisto.criteria.ClienteCriteria;
import com.setlisto.model.Cliente;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.SQLUtils;

public class ClienteDAO {
	
	private static Logger logger = LogManager.getLogger(ClienteDAO.class.getName());

	private static final String BASE_QUERY = " SELECT id, preferences, email, password, name, phone, "
			+ " surname1, active, created_at, updated_at, verified, birth_date "
			+ " FROM customer ";

	public ClienteDAO() {
	}

	public Cliente findById (Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			Cliente cli = null;
			if (rs.next()) {
				cli = loadNext(rs);
			}
			return cli;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public Cliente findByEmail(String email) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE LOWER(email) = LOWER(?) ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, email);
			rs = ps.executeQuery();

			Cliente cli = null;
			if (rs.next()) {
				cli = loadNext(rs);
			}
			return cli;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public boolean emailExists(String email) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			c = DAOUtils.getConnection();

			String sql = " SELECT 1 FROM customer WHERE LOWER(email) = LOWER(?) LIMIT 1 ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, email);
			rs = ps.executeQuery();

			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public List<Cliente> findAll() {
		List<Cliente> clientes = new ArrayList<>(); 
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" ORDER BY id ");


			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			while (rs.next()) {
				clientes.add(loadNext(rs));
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return clientes;
	}

	public boolean isVerified(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {

			c = DAOUtils.getConnection();

			String sql = " SELECT verified FROM customer cu WHERE cu.id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getBoolean(1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public boolean setVerify(boolean verified, Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

			String sql = " UPDATE customer SET verified = ? WHERE id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, verified, id);

			int rows = ps.executeUpdate();
			return rows > 0;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public boolean setActive(boolean active, Long customerId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		try {
			c = DAOUtils.getConnection();

			String sql = " UPDATE customer SET active = ? WHERE id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, active, customerId);

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public int countReviews(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null; 
		int total = 0;
		try {
			c = DAOUtils.getConnection();

			String sql = " SELECT COUNT(*) FROM review WHERE customer_id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt(1); 
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return total;
	}

	public boolean hasReviewedEvent(Long customerId, Long eventId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

			String sql = " SELECT COUNT(*) FROM review WHERE customer_id = ? AND musical_event_id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, customerId, eventId);
			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);
				return count > 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public Cliente create(Cliente cliente) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO customer (preferences, email, password, name, ");
			sql.append(" phone, surname1, active, verified, birth_date) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			ps = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

			DAOUtils.setParameters(ps,
					cliente.getPreferencias(),
					cliente.getEmail(),
					cliente.getContrasena(),
					cliente.getNombre(),
					cliente.getTelefono(),
					cliente.getApellido(),
					cliente.getActivo(),
					cliente.getVerificado(),
					cliente.getFechaNacimiento()
					);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					// Se asigna el ID generado al objeto original
					cliente.setId(rs.getLong(1));
				}
				return cliente; // Se devuelve el objeto completo
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public boolean update(Cliente cliente) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE customer SET preferences = ?, email = ?, password = ?, ");
			sql.append(" name = ?, phone = ?, surname1 = ?, birth_date = ? WHERE id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps,
					cliente.getPreferencias(),
					cliente.getEmail(),
					cliente.getContrasena(),
					cliente.getNombre(),
					cliente.getTelefono(),
					cliente.getApellido(),
					cliente.getFechaNacimiento(),
					cliente.getId()
					);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public boolean delete(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			String sql = "DELETE FROM customer WHERE id = ?";

			ps = c.prepareStatement(sql);

			DAOUtils.setParameters(ps, id);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public Results<Cliente> findByCriteria(ClienteCriteria criteria, int from, int pageSize) {
		logger.info("Criteria: {}", criteria);
		
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Results<Cliente> results = new Results<>();

		try {
			c = DAOUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			SQLUtils.addClause(criteria.getActivo(), condiciones, " active = ?  ", parametros, criteria.getActivo());
			SQLUtils.addClause(criteria.getApellido(), condiciones, " UPPER(surname1) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getApellido()));
			SQLUtils.addClause(criteria.getEmail(), condiciones," UPPER(email) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getEmail()));
			SQLUtils.addClause(criteria.getNombre(), condiciones, " UPPER(name) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getNombre()));
			SQLUtils.addClause(criteria.getVerificado(), condiciones, " verified = ? ", parametros, criteria.getVerificado());
			SQLUtils.addClause(criteria.getCreadoDesde(), condiciones, " created_at >= ? ", parametros, criteria.getCreadoDesde());
			SQLUtils.addClause(criteria.getCreadoHasta(), condiciones, " created_at <= ? ", parametros, criteria.getCreadoHasta());
			SQLUtils.addClause(criteria.getTelefono(), condiciones, " phone LIKE ? ", parametros, SQLUtils.like(criteria.getTelefono()));
			SQLUtils.addClause(criteria.getFechaNacimientoDesde(), condiciones, " birth_date >= ? ", parametros, criteria.getFechaNacimientoDesde());
			SQLUtils.addClause(criteria.getFechaNacimientoHasta(), condiciones, " birth_date <= ? ", parametros, criteria.getFechaNacimientoHasta());

			if (!condiciones.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(String.join(" AND ", condiciones));
			}
			
			sql.append(" ORDER BY ");
			sql.append(criteria.getOrderBy() == null || criteria.getOrderBy().trim().isEmpty() ? " id " : criteria.getOrderBy());
			sql.append(Boolean.FALSE.equals(criteria.getAscDesc()) ? " DESC " : " ASC ");
			
			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}: {}:", criteria, sql);
			};

			ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			DAOUtils.setParameters(ps, parametros);

			rs = ps.executeQuery();
			List<Cliente> resultsPage = new ArrayList<>();
			
			int filaInicial = Math.max(1, from + 1);
			if (rs.absolute(filaInicial)) {
				int count = 0;
				do {
					resultsPage.add(loadNext(rs));
					count++;
				} while (count < pageSize && rs.next());
			}
			int totalResults = SQLUtils.getTotalRows(rs);
			
			results.setPage(resultsPage);
			results.setTotal(totalResults);
			
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	private static Cliente loadNext(ResultSet rs) throws Exception {
		int i = 1;
		Cliente cli = new Cliente();
		cli.setId(rs.getLong(i++));
		cli.setPreferencias(rs.getString(i++));
		cli.setEmail(rs.getString(i++));
		cli.setContrasena(rs.getString(i++));
		cli.setNombre(rs.getString(i++));
		cli.setTelefono(rs.getString(i++));
		cli.setApellido(rs.getString(i++));
		cli.setActivo(rs.getBoolean(i++));
		cli.setFechaCreacion(rs.getTimestamp(i++).toLocalDateTime());
		Timestamp ts = rs.getTimestamp(i++);
		cli.setFechaModificacion(ts != null ? ts.toLocalDateTime() : null);
		cli.setVerificado(rs.getBoolean(i++));
		cli.setFechaNacimiento(rs.getDate(i++));
		return cli;
	}
}

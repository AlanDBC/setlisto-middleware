package com.setlisto.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;
import com.setlisto.utils.SQLUtils;

public class OrganizadorDAO {

	private static Logger logger = LogManager.getLogger(OrganizadorDAO.class.getName());

	private static final String BASE_QUERY = " SELECT org.id, org.business_name, org.verified, org.email, org.phone, "
			+ " org.name, org.surname1, org.surname2, org.password, org.birth_date "
			+ " FROM organizer org ";

	public OrganizadorDAO() {
	}

	public Organizador findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE org.id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();

			Organizador ogr = null;
			while (rs.next()) {
				ogr = loadNext(rs);
			}
			return ogr;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Organizador findByEmail(Connection c, String email) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE LOWER(org.email) = LOWER(?) ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, email);
			rs = ps.executeQuery();

			Organizador organizador = null;
			if (rs.next()) {
				organizador = loadNext(rs);
			}
			return organizador;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.findByEmail con email {}: {}", email, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Results<Organizador> findByCriteria(Connection c, OrganizadorCriteria criteria, int from, int pageSize) throws DataException {
		logger.info("Criteria: {}", criteria);

		PreparedStatement ps = null;
		ResultSet rs = null;

		Results<Organizador> results = new Results<Organizador>();

		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			SQLUtils.addClause(criteria.getId(), condiciones, " org.id = ? ", parametros, criteria.getId());
			SQLUtils.addClause(criteria.getNombreComercial(), condiciones, " UPPER(business_name) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getNombreComercial()));
			SQLUtils.addClause(criteria.getVerificado(), condiciones, " verified = ? ", parametros, criteria.getVerificado());
			SQLUtils.addClause(criteria.getEmail(), condiciones, " UPPER(email) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getEmail()));
			SQLUtils.addClause(criteria.getTelefono(), condiciones, " phone LIKE ? ", parametros, SQLUtils.like(criteria.getTelefono()));
			SQLUtils.addClause(criteria.getNombre(), condiciones, " UPPER(name) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getNombre()));
			SQLUtils.addClause(criteria.getApellido1(), condiciones, " UPPER(surname1) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getApellido1()));
			SQLUtils.addClause(criteria.getApellido2(), condiciones, " UPPER(surname2) LIKE UPPER(?) ", parametros, SQLUtils.like(criteria.getApellido2()));
			SQLUtils.addClause(criteria.getFechaNacimientoDesde(), condiciones, " birth_date >= ? ", parametros, criteria.getFechaNacimientoDesde());
			SQLUtils.addClause(criteria.getFechaNacimientoHasta(), condiciones, " birth_date <= ? ", parametros, criteria.getFechaNacimientoHasta());

			if (!condiciones.isEmpty()) {
				sql.append(" WHERE ");
				sql.append(String.join(" AND ", condiciones));
			}

			sql.append(" ORDER BY ");
			sql.append(criteria.getOrderBy() == null || criteria.getOrderBy().trim().isEmpty() ? " org.id " : criteria.getOrderBy());
			sql.append(Boolean.FALSE.equals(criteria.getAscDesc()) ? " DESC " : " ASC ");

			if (logger.isInfoEnabled()) {
				logger.info("Criteria SQL: {}: {}:", criteria, sql);
			} 

			ps = c.prepareStatement(sql.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			DAOUtils.setParameters(ps, parametros);

			rs = ps.executeQuery();
			List<Organizador> resultsPage = new ArrayList<Organizador>();

			int filaInicial = Math.max(1, from + 1);
			 
			if (rs.absolute(filaInicial)) {
				int count = 0;
				do {
					resultsPage.add(loadNext(rs));
					count++;
				} while (count<pageSize && rs.next()); 
			}
			
			int totalResults = SQLUtils.getTotalRows(rs);
			results.setPage(resultsPage);
			results.setTotal(totalResults);

			return results;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.findByCriteria con criteria {}: {}", criteria, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}
	
	public List<Organizador> findAll(Connection c) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {			
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			
			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			List<Organizador> organizadores = new ArrayList<Organizador>();
			while (rs.next()) {
				organizadores.add(loadNext(rs));
			}
			return organizadores;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.findAll: {}", e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public boolean updateVerifiedStatus(Connection c, Long id, boolean verificado) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "UPDATE organizer SET verified = ? WHERE id = ?";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, verificado, id);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.updateVerifiedStatus con ID {} y valor verificado {}: {}", id, verificado, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Organizador create(Connection c, Organizador organizador) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO organizer (business_name, verified, email, ");
			sql.append(" password, phone, name, surname1, surname2, birth_date) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			ps = c.prepareStatement(sql.toString(), PreparedStatement.RETURN_GENERATED_KEYS);

			DAOUtils.setParameters(ps,
					organizador.getNombreComercial(),
					organizador.getVerificado(),
					organizador.getEmail(),
					organizador.getContrasena(),
					organizador.getTelefono(),
					organizador.getNombre(),
					organizador.getApellido1(),
					organizador.getApellido2(),
					organizador.getFechaNacimiento()
					);

			int rows = ps.executeUpdate();

			if (rows > 0) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					// Asignación del ID generado al POJO
					organizador.setId(rs.getLong(1));
				}
				return organizador; // Retorno de la entidad enriquecida
			}
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.create con organizador {}: {}", organizador, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
		return null;
	}

	public boolean update(Connection c, Organizador organizador) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "UPDATE organizer SET business_name = ?, verified = ?, email = ?, phone = ?, "
					+ "name = ?, surname1 = ?, surname2 = ?, birth_date = ? WHERE id = ?";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, 
					organizador.getNombreComercial(),
					organizador.getVerificado(),
					organizador.getEmail(), 
					organizador.getTelefono(), 
					organizador.getNombre(), 
					organizador.getApellido1(),
					organizador.getApellido2(),
					organizador.getFechaNacimiento(),
					organizador.getId()
					);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.update con organizador {}: {}", organizador, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public boolean delete(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "DELETE FROM organizer WHERE id = ?";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, id);

			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (SQLException e) {
			logger.error("Error en OrganizadorDAO.update con ID{}: {}", id, e.getMessage());
		    throw new DataException(e);
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	private Organizador loadNext(ResultSet rs) throws SQLException {
		Organizador org = new Organizador();
		int i = 1; 
		org.setId(rs.getLong(i++));
		org.setNombreComercial(rs.getString(i++));
		org.setVerificado(rs.getBoolean(i++));
		org.setEmail(rs.getString(i++));
		org.setTelefono(rs.getString(i++));
		org.setNombre(rs.getString(i++));
		org.setApellido1(rs.getString(i++));
		org.setApellido2(rs.getString(i++));
		org.setContrasena(rs.getString(i++));
		org.setFechaNacimiento(rs.getDate(i++));
		return org;
	}
}

package com.setlisto.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.criteria.OrganizadorCriteria;
import com.setlisto.model.Organizador;
import com.setlisto.model.Results;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.SQLUtils;

public class OrganizadorDAO {

	private static Logger logger = LogManager.getLogger(OrganizadorDAO.class.getName()); // TODO

	private static final String BASE_QUERY = " SELECT org.id, org.business_name, org.verified, org.email, org.phone, "
			+ " org.name, org.surname1, org.surname2, org.password, org.birth_date "
			+ " FROM organizer org ";

	public OrganizadorDAO() {
	}

	public Organizador findById(Long id) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();

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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public Results<Organizador> findByCriteria(OrganizadorCriteria criteria, int from, int pageSize) {
		logger.info("Criteria: {}", criteria);

		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Results<Organizador> results = new Results<Organizador>();

		try {
			c = DAOUtils.getConnection();

			StringBuilder sql = new StringBuilder(BASE_QUERY);

			List<String> condiciones = new ArrayList<>();
			List<Object> parametros = new ArrayList<>();

			SQLUtils.addClause(criteria.getId(), condiciones, " org.id = ? ", parametros, criteria.getId());
			SQLUtils.addClause(criteria.getNombreComercial(), condiciones, " UPPER(business_name) = UPPER(?) ", parametros, criteria.getNombreComercial());
			SQLUtils.addClause(criteria.getVerificado(), condiciones, " verified = ? ", parametros, criteria.getVerificado());
			SQLUtils.addClause(criteria.getEmail(), condiciones, " UPPER(email) = UPPER(?) ", parametros, criteria.getEmail());
			SQLUtils.addClause(criteria.getTelefono(), condiciones, " phone = ? ", parametros, criteria.getTelefono());
			SQLUtils.addClause(criteria.getNombre(), condiciones, " UPPER(name) = UPPER(?) ", parametros, criteria.getNombre());
			SQLUtils.addClause(criteria.getApellido1(), condiciones, " UPPER(surname1) = UPPER(?) ", parametros, criteria.getApellido1());
			SQLUtils.addClause(criteria.getApellido2(), condiciones, " UPPER(surname2) = UPPER(?) ", parametros, criteria.getApellido2());
			SQLUtils.addClause(criteria.getFechaNacimientoDesde(), condiciones, " birth_date >= ? ", parametros, criteria.getFechaNacimientoDesde());
			SQLUtils.addClause(criteria.getFechaNacimientoHasta(), condiciones, " birth_date <= ? ", parametros, criteria.getFechaNacimientoHasta());

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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}
	
	public List<Organizador> findAll() {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			c = DAOUtils.getConnection();
			
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			
			ps = c.prepareStatement(sql.toString());
			rs = ps.executeQuery();

			List<Organizador> organizadores = new ArrayList<Organizador>();
			while (rs.next()) {
				organizadores.add(loadNext(rs));
			}
			return organizadores;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public boolean updateVerifiedStatus(Long id, boolean verificado) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			String sql = "UPDATE organizer SET verified = ? WHERE id = ?";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, verificado, id);
			int rows = ps.executeUpdate();
			return rows > 0;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	public Organizador create(Organizador organizador) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return null;
	}

	public boolean update(Organizador organizador) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			String sql = "UPDATE organizer SET business_name = ?, verified = ?, email = ?, phone = ?, "
					+ "name = ?, surname1 = ?, surname2 = ?. birth_date = ? WHERE id = ?";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, 
					organizador.getNombreComercial(),
					organizador.getVerificado(),
					organizador.getEmail(), 
					organizador.getTelefono(), 
					organizador.getNombre(), 
					organizador.getApellido1(),
					organizador.getApellido2(),
					organizador.getId(),
					organizador.getFechaNacimiento()
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
			String sql = "DELETE FROM organizer WHERE id = ?";

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

	private Organizador loadNext(ResultSet rs) throws Exception {
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

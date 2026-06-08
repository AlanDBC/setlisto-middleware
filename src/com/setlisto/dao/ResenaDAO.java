package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.DataException;
import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;
import com.setlisto.service.test.EstadoEventoServiceTest;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class ResenaDAO {

	private static Logger logger = LogManager.getLogger(ResenaDAO.class.getName());

	private static String BASE_QUERY = " SELECT r.musical_event_id, me.name, r.customer_id, cu.name,"
			+ " r.stars, r.comment, r.favorite, r.created_at, r.updated_at "
			+ " FROM review r "
			+ " JOIN musical_event me ON r.musical_event_id = me.id "
			+ " JOIN customer cu ON r.customer_id = cu.id ";

	public ResenaDAO() {
	}

	/**
	 * Crea una reseña, un usuario solo puede crear una reseña por cada evento al que asista.
	 * De querer hacer otra, deberá editar la ya existente.
	 * @param resena (resenaDTO, pasando solo los atributos POJO)
	 * @return boolean (true si se ha creado correctamente, false en caso contrario)
	 */
	public boolean create(Connection c, Resena resena) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" INSERT INTO review (musical_event_id, customer_id, stars ");
			sql.append(" , favorite, comment) VALUES (?, ?, ?, ?, ?)");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, 
					resena.getEventoId(),
					resena.getClienteId(),
					resena.getEstrellas(),
					resena.getFavorito(),
					resena.getComentario()
					);

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.create con resena {}: {}", resena, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs,ps);
		}
	}

	/**
	 * Edita una reseña existente. 
	 * @param resenaDTO con los datos a editar
	 * @return true si se ha editado correctamente, false en caso contrario
	 */
	public boolean edit(Connection c, Resena resena) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" UPDATE review SET stars = ?, favorite = ?, comment = ? ");
			sql.append(" WHERE musical_event_id = ? AND customer_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, 
					resena.getEstrellas(),
					resena.getFavorito(),
					resena.getComentario(),
					resena.getEventoId(),
					resena.getClienteId()
					);

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.edit con resena {}: {}", resena, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	/**
	 * Elimina una reseña existente.
	 * @param eventoId
	 * @param usuarioId
	 * @return true si se ha eliminado correctamente, false en caso contrario
	 */
	public boolean delete(Connection c, Long eventoId, Long usuarioId) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = " DELETE FROM review WHERE musical_event_id = ? AND customer_id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, eventoId, usuarioId);

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.delete con evento Id {} y usuario Id: {}", eventoId, usuarioId, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs,ps);
		}
	}

	/**
	 * Busca una reseña por evento y usuario.
	 * @param eventoId
	 * @param usuarioId
	 * @return ResenaDTO si se encuentra, null en caso contrario
	 */
	public ResenaDTO findByEventAndCustomer(Connection c, Long eventoId, Long usuarioId) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE r.musical_event_id = ? AND r.customer_id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, eventoId, usuarioId);
			rs = ps.executeQuery();

			ResenaDTO resena = null;
			if (rs.next()) {
				resena = loadNext(rs);
			}
			return resena;
		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.findByEventAndCustomer con evento Id {} y usuario Id: {}", eventoId, usuarioId, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs,ps);
		}
	}

	/**
	 * Busca todas las reseñas de un evento.
	 * @param eventoId
	 * @return Lista de ResenaDTO
	 */
	public List<ResenaDTO> findByMusicalEvent(Connection c, Long eventoId) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE r.musical_event_id = ? ");

			ps = c.prepareStatement(sql.toString());

			DAOUtils.setParameters(ps, eventoId);
			rs = ps.executeQuery();

			List<ResenaDTO> resenas = new ArrayList<>();

			while (rs.next()) {
				resenas.add(loadNext(rs));
			}
			return resenas;
		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.findByMusicalEvent con evento Id {}: {}", eventoId, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs,ps);
		}
	}

	/**
	 * Busca todas las reseñas de un usuario.
	 * @param usuarioId
	 * @return Lista de ResenaDTO
	 */
	public List<ResenaDTO> findByCustomer(Connection c, Long usuarioId) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE r.customer_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, usuarioId);
			rs = ps.executeQuery();

			List<ResenaDTO> resenas = new ArrayList<>();

			while (rs.next()) {
				resenas.add(loadNext(rs));
			}
			return resenas;
		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.findByCustomer con cliente Id {}: {}", usuarioId, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs,ps);
		}
	}

	/**
	 * Comprueba si un evento es favorito para un usuario.
	 * @param eventoId
	 * @param usuarioId
	 * @return true si es favorito, false en caso contrario
	 */
	public boolean isFavorite(Connection c, Long eventoId, Long usuarioId) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = " SELECT favorite FROM review WHERE musical_event_id = ? AND customer_id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, eventoId, usuarioId);

			rs = ps.executeQuery();

			if (rs.next()) {
				int i = 1;
				return rs.getBoolean(i++);
			}
		} catch (SQLException e) {
			logger.error("Error en ResenaDAO.isFavorite con evento Id {} y cliente Id {}: {}", eventoId, usuarioId, e.getMessage());
		    throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs,ps);
		}
		return false;
	}
	
	private ResenaDTO loadNext (ResultSet rs) throws SQLException {
		int i = 1;
		ResenaDTO resena = new ResenaDTO();
		resena.setEventoId(rs.getLong(i++));
		resena.setNombreEvento(rs.getString(i++));
		resena.setClienteId(rs.getLong(i++));
		resena.setNombreUsuario(rs.getString(i++));
		resena.setEstrellas(rs.getDouble(i++));
		resena.setComentario(rs.getString(i++));
		resena.setFavorito(rs.getBoolean(i++));
		Timestamp creacionTs = rs.getTimestamp(i++);
		resena.setFechaCreacion(creacionTs != null ? creacionTs.toLocalDateTime() : null);
		Timestamp modificacionTs = rs.getTimestamp(i++);
		resena.setFechaModificacion(modificacionTs != null ? modificacionTs.toLocalDateTime() : null);
		return resena;
	}
}

package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.Resena;
import com.setlisto.model.ResenaDTO;
import com.setlisto.utils.DAOUtils;

public class ResenaDAO {

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
	public boolean create(Resena resena) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;	
		try {
			c = DAOUtils.getConnection();
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps, c);
		}
		return false;
	}

	/**
	 * Edita una reseña existente. 
	 * @param resenaDTO con los datos a editar
	 * @return true si se ha editado correctamente, false en caso contrario
	 */
	public boolean edit(Resena resena) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs, ps, c);
		}
		return false;
	}

	/**
	 * Elimina una reseña existente.
	 * @param eventoId
	 * @param usuarioId
	 * @return true si se ha eliminado correctamente, false en caso contrario
	 */
	public boolean delete(Long eventoId, Long usuarioId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			String sql = " DELETE FROM review WHERE musical_event_id = ? AND customer_id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, eventoId, usuarioId);

			int rows = ps.executeUpdate();
			return rows > 0;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps, c);
		}
		return false;
	}

	/**
	 * Busca una reseña por evento y usuario.
	 * @param eventoId
	 * @param usuarioId
	 * @return ResenaDTO si se encuentra, null en caso contrario
	 */
	public ResenaDTO findByEventAndCustomer(Long eventoId, Long usuarioId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps, c);
		}
		return null;
	}

	/**
	 * Busca todas las reseñas de un evento.
	 * @param eventoId
	 * @return Lista de ResenaDTO, null en caso de error o inexistencia
	 */
	public List<ResenaDTO> findByMusicalEvent(Long eventoId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps, c);
		}
		return null;
	}

	/**
	 * Busca todas las reseñas de un usuario.
	 * @param usuarioId
	 * @return Lista de ResenaDTO
	 */
	public List<ResenaDTO> findByCustomer(Long usuarioId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			StringBuilder sql = new StringBuilder(BASE_QUERY);
			sql.append(" WHERE r.customer_id = ? ");

			ps = c.prepareStatement(sql.toString());
			DAOUtils.setParameters(ps, usuarioId);
			rs = ps.executeQuery();

			// Lista de reseñas
			List<ResenaDTO> resenas = new ArrayList<>();

			while (rs.next()) {
				resenas.add(loadNext(rs));
			}
			return resenas;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps, c);
		}
		return null;
	}

	/**
	 * Comprueba si un evento es favorito para un usuario.
	 * @param eventoId
	 * @param usuarioId
	 * @return true si es favorito, false en caso contrario
	 */
	public boolean isFavorite(Long eventoId, Long usuarioId) {
		Connection c = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			c = DAOUtils.getConnection();
			String sql = " SELECT favorite FROM review WHERE musical_event_id = ? AND customer_id = ? ";

			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, eventoId, usuarioId);

			rs = ps.executeQuery();

			if (rs.next()) {
				int i = 1;
				return rs.getBoolean(i++);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DAOUtils.close(rs,ps, c);
		}
		return false;
	}
	
	private ResenaDTO loadNext (ResultSet rs) throws Exception {
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

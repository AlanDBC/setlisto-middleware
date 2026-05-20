package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.ZonaEvento;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

/*
 * Representa un rectangulo dibujado en el plano del evento musical, este contiene una categoria, cantidad y precio
 * por el momento las reservas se hacen directamente hacia la zona y no hacia una plaza en concreto.
 */
public class ZonaEventoDAO {

	private static Logger logger = LogManager.getLogger(ZonaEventoDAO.class.getName());

	private static final String BASE_QUERY =
			" SELECT ez.id, ez.musical_event_id, ez.seat_category_id, sc.name, ez.section_name, "
					+ " ez.total_capacity, ez.available_capacity, ez.base_price, "
					+ " ez.pos_x, ez.pos_y, ez.width, ez.height "
					+ " FROM event_zone ez "
					+ " INNER JOIN seat_category sc ON sc.id = ez.seat_category_id ";

	public ZonaEvento findById(Connection c, Long id) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(BASE_QUERY + " WHERE ez.id = ? ");
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();
			return rs.next() ? loadNext(rs) : null;
		} catch(SQLException e) {
			logger.error("Error en ZonaEventoDAO.findById con ID {}: {}", id, e.getMessage());
			throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<ZonaEvento> findByEventId(Connection c, Long musicalEventId) throws DataException {
		List<ZonaEvento> zones = new ArrayList<ZonaEvento>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(BASE_QUERY + " WHERE ez.musical_event_id = ? ORDER BY ez.id ");
			DAOUtils.setParameters(ps, musicalEventId);
			rs = ps.executeQuery();
			while (rs.next()) {
				zones.add(loadNext(rs));
			}
			return zones;
		} catch(SQLException e) {
			logger.error("Error en ZonaEventoDAO.findByEventId con ID {}: {}", musicalEventId, e.getMessage());
			throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Long create(Connection c, ZonaEvento zone) throws DataException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			StringBuilder sql = new StringBuilder(" INSERT INTO event_zone (musical_event_id, seat_category_id, section_name, ");
			sql.append(" total_capacity, available_capacity, base_price, pos_x, pos_y, width, height) ");
			sql.append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");

			ps = c.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			DAOUtils.setParameters(ps,
					zone.getEventoId(),
					zone.getCategoriaAsientoId(),
					zone.getSeccionNombre(),
					zone.getCapacidadTotal(),
					zone.getCapacidadDisponible(),
					zone.getPrecioBase(),
					zone.getPosX(),
					zone.getPosY(),
					zone.getAncho(),
					zone.getAlto());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				zone.setId(rs.getLong(1));
				return zone.getId();
			}
			return null;
		} catch(SQLException e) {
			logger.error("Error en ZonaEventoDAO.create con zona {}: {}", zone, e.getMessage());
			throw new DataException(e); 
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public void replaceByEventId(Connection c, Long musicalEventId, List<ZonaEvento> zones) throws DataException {
		deleteByEventId(c, musicalEventId);
		if (zones == null) {
			return;
		}
		for (ZonaEvento zone : zones) {
			zone.setEventoId(musicalEventId);
			if (zone.getCapacidadDisponible() == null) {
				zone.setCapacidadDisponible(zone.getCapacidadTotal());
			}
			create(c, zone);
		}
	}

	public void deleteByEventId(Connection c, Long musicalEventId) throws DataException {
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(" DELETE FROM event_zone WHERE musical_event_id = ? ");
			DAOUtils.setParameters(ps, musicalEventId);
			ps.executeUpdate();
		} catch(SQLException e) {

		} finally {
			JDBCUtils.close(null, ps);
		}
	}

	public boolean reserveCapacity(Connection c, Long eventZoneId, int quantity) throws DataException {
		PreparedStatement ps = null;
		try {
			String sql = " UPDATE event_zone "
					+ " SET available_capacity = available_capacity - ? "
					+ " WHERE id = ? AND available_capacity >= ? ";
			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, quantity, eventZoneId, quantity);
			return ps.executeUpdate() > 0;
		} catch(SQLException e) {
			logger.error("Error en ZonaEventoDAO.reserveCapacity con zona ID {} y cantidad {}: {}", eventZoneId, quantity, e.getMessage());
			throw new DataException(e); 
		}
		finally {
			JDBCUtils.close(null, ps);
		}
	}

	private ZonaEvento loadNext(ResultSet rs) throws SQLException {
		int i = 1;
		ZonaEvento zone = new ZonaEvento();
		zone.setId(rs.getLong(i++));
		zone.setEventoId(rs.getLong(i++));
		zone.setCategoriaAsientoId(rs.getLong(i++));
		zone.setCategoriaAsientoNombre(rs.getString(i++));
		zone.setSeccionNombre(rs.getString(i++));
		zone.setCapacidadTotal(rs.getInt(i++));
		zone.setCapacidadDisponible(rs.getInt(i++));
		zone.setPrecioBase(rs.getBigDecimal(i++));
		zone.setPosX(rs.getInt(i++));
		zone.setPosY(rs.getInt(i++));
		zone.setAncho(rs.getInt(i++));
		zone.setAlto(rs.getInt(i++));
		return zone;
	}
}

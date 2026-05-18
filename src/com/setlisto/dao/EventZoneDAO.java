package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.EventZone;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class EventZoneDAO {

	private static final String BASE_QUERY =
			" SELECT ez.id, ez.musical_event_id, ez.seat_category_id, sc.name, ez.section_name, "
					+ " ez.total_capacity, ez.available_capacity, ez.base_price, "
					+ " ez.pos_x, ez.pos_y, ez.width, ez.height "
					+ " FROM event_zone ez "
					+ " INNER JOIN seat_category sc ON sc.id = ez.seat_category_id ";

	public EventZone findById(Connection c, Long id) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = c.prepareStatement(BASE_QUERY + " WHERE ez.id = ? ");
			DAOUtils.setParameters(ps, id);
			rs = ps.executeQuery();
			return rs.next() ? loadNext(rs) : null;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public List<EventZone> findByEventId(Connection c, Long musicalEventId) throws Exception {
		List<EventZone> zones = new ArrayList<EventZone>();
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
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public Long create(Connection c, EventZone zone) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = " INSERT INTO event_zone (musical_event_id, seat_category_id, section_name, "
					+ " total_capacity, available_capacity, base_price, pos_x, pos_y, width, height) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			DAOUtils.setParameters(ps,
					zone.getMusicalEventId(),
					zone.getSeatCategoryId(),
					zone.getSectionName(),
					zone.getTotalCapacity(),
					zone.getAvailableCapacity(),
					zone.getBasePrice(),
					zone.getPosX(),
					zone.getPosY(),
					zone.getWidth(),
					zone.getHeight());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				zone.setId(rs.getLong(1));
				return zone.getId();
			}
			return null;
		} finally {
			JDBCUtils.close(rs, ps);
		}
	}

	public void replaceByEventId(Connection c, Long musicalEventId, List<EventZone> zones) throws Exception {
		deleteByEventId(c, musicalEventId);
		if (zones == null) {
			return;
		}
		for (EventZone zone : zones) {
			zone.setMusicalEventId(musicalEventId);
			if (zone.getAvailableCapacity() == null) {
				zone.setAvailableCapacity(zone.getTotalCapacity());
			}
			create(c, zone);
		}
	}

	public void deleteByEventId(Connection c, Long musicalEventId) throws Exception {
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement(" DELETE FROM event_zone WHERE musical_event_id = ? ");
			DAOUtils.setParameters(ps, musicalEventId);
			ps.executeUpdate();
		} finally {
			JDBCUtils.close(null, ps);
		}
	}

	public boolean reserveCapacity(Connection c, Long eventZoneId, int quantity) throws Exception {
		PreparedStatement ps = null;
		try {
			String sql = " UPDATE event_zone "
					+ " SET available_capacity = available_capacity - ? "
					+ " WHERE id = ? AND available_capacity >= ? ";
			ps = c.prepareStatement(sql);
			DAOUtils.setParameters(ps, quantity, eventZoneId, quantity);
			return ps.executeUpdate() > 0;
		} finally {
			JDBCUtils.close(null, ps);
		}
	}

	private EventZone loadNext(ResultSet rs) throws Exception {
		int i = 1;
		EventZone zone = new EventZone();
		zone.setId(rs.getLong(i++));
		zone.setMusicalEventId(rs.getLong(i++));
		zone.setSeatCategoryId(rs.getLong(i++));
		zone.setSeatCategoryName(rs.getString(i++));
		zone.setSectionName(rs.getString(i++));
		zone.setTotalCapacity(rs.getInt(i++));
		zone.setAvailableCapacity(rs.getInt(i++));
		zone.setBasePrice(rs.getBigDecimal(i++));
		zone.setPosX(rs.getInt(i++));
		zone.setPosY(rs.getInt(i++));
		zone.setWidth(rs.getInt(i++));
		zone.setHeight(rs.getInt(i++));
		return zone;
	}
}

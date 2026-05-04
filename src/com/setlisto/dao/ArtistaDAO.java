package com.setlisto.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.Artista;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class ArtistaDAO {

    private static final String BASE_QUERY = " SELECT id, name, description FROM artist ";

    public Artista findById(Long id) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = JDBCUtils.getConnection();
            String sql = BASE_QUERY + " WHERE id = ? ";
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return loadNext(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAOUtils.close(rs, ps, c);
        }
        return null;
    }

    /**
     * Recupera todos los artistas que participan en un evento musical concreto.
     */
    public List<Artista> findByMusicalEventId(Long eventId) {
        List<Artista> artistas = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = JDBCUtils.getConnection();
            String sql = " SELECT a.id, a.name, a.description " +
                         " FROM artist a " +
                         " JOIN musical_event_artist mea ON a.id = mea.artist_id " +
                         " WHERE mea.musical_event_id = ? ";
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, eventId);
            rs = ps.executeQuery();
            while (rs.next()) {
                artistas.add(loadNext(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAOUtils.close(rs, ps, c);
        }
        return artistas;
    }

    public Artista create(Artista artista) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = JDBCUtils.getConnection();
            String sql = " INSERT INTO artist (name, description) VALUES (?, ?) ";
            ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            DAOUtils.setParameters(ps, artista.getNombre(), artista.getDescripcion());
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    artista.setId(rs.getLong(1));
                }
                return artista;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAOUtils.close(rs, ps, c);
        }
        return null;
    }

    private Artista loadNext(ResultSet rs) throws SQLException {
        Artista a = new Artista();
        int i = 1;
        a.setId(rs.getLong(i++));
        a.setNombre(rs.getString(i++));
        a.setDescripcion(rs.getString(i++));
        return a;
    }
}

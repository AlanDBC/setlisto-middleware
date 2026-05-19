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

import com.setlisto.model.Artista;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class ArtistaDAO {
	
	private static final Logger logger = LogManager.getLogger(ArtistaDAO.class.getName());

    private static final String BASE_QUERY = " SELECT id, name, description FROM artist ";

    public Artista findById(Connection c, Long id) throws DataException {    
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " WHERE id = ? ";
            ps = c.prepareStatement(sql);
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return loadNext(rs);
            }
        } catch (SQLException e) {
        	logger.error("Error en ArtistaDAO.findById con ID {}: {}", id, e.getMessage());
            throw new DataException(e);
        } finally {
            JDBCUtils.close(rs, ps);
        }
        return null;
    }

    /**
     * Recupera todos los artistas que participan en un evento musical concreto.
     */
    public List<Artista> findByMusicalEventId(Connection c,Long eventId) throws DataException {
        List<Artista> artistas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
        } catch (SQLException e) {
        	logger.error("Error buscando por evento con id {}: {}", eventId, e.getMessage(), e);
        	throw new DataException(e);
        } finally {
            JDBCUtils.close(rs, ps);
        }
        return artistas;
    }

    public Artista create(Connection c,Artista artista) throws DataException {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
        } catch (SQLException e) {
        	logger.error("Error al crear artista {}: {}", artista.getNombre(), e.getMessage());
            throw new DataException(e);
        } finally {
        	JDBCUtils.close(rs, ps);
        }
        return null;
    }
    
    public List<Artista> findAll(Connection c) throws DataException {
        List<Artista> artistas = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = BASE_QUERY + " ORDER BY name ";
            ps = c.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                artistas.add(loadNext(rs));
            }
        } catch (SQLException e) {
        	logger.error("Error en ArtistaDAO.findAll: {}", e.getMessage());
            throw new DataException(e);
        } finally {
        	JDBCUtils.close(rs, ps);
        }

        return artistas;
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

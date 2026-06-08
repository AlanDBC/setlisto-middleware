package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.DataException;
import com.setlisto.model.EstadoPlaza;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;


public class EstadoPlazaDAO {
	private static Logger logger = LogManager.getLogger(EstadoPlazaDAO.class.getName());

    private static final String BASE_QUERY = " SELECT id, name FROM seat_status ";

    public EstadoPlazaDAO() {
    }

    public EstadoPlaza findById(Connection c, Long id)  throws DataException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder(BASE_QUERY);
            sql.append(" WHERE id = ? ");

            ps = c.prepareStatement(sql.toString());
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();

            EstadoPlaza ep = null;
            if (rs.next()) {
                ep = loadNext(rs);
            }
            return ep;
        } catch (SQLException e) {
        	logger.error("Error en EstadoPlazaDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
        } finally {
        	JDBCUtils.close(rs, ps);
        }
    }

    public List<EstadoPlaza> findAll(Connection c) throws DataException {
        List<EstadoPlaza> resultados = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = c.prepareStatement(BASE_QUERY);
            rs = ps.executeQuery();

            while (rs.next()) {
                resultados.add(loadNext(rs));
            }
            return resultados;
        } catch (SQLException e) {
        	logger.error("Error en EstadoPlazaDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
        } finally {
        	JDBCUtils.close(rs, ps);
        }
    }

    private EstadoPlaza loadNext(ResultSet rs) throws SQLException {
        int i = 1;
        EstadoPlaza ep = new EstadoPlaza();
        ep.setId(rs.getLong(i++));
        ep.setNombre(rs.getString(i++));
        return ep;
    }
}
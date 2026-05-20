package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.setlisto.model.TipoTicket;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class TipoTicketDAO {

	private static Logger logger = LogManager.getLogger(TipoTicketDAO.class.getName());

    private static final String BASE_QUERY = " SELECT id, name FROM ticket_type ";

    public TipoTicketDAO() {
    }

    public TipoTicket findById(Connection c, Long id) throws DataException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            StringBuilder sql = new StringBuilder(BASE_QUERY);
            sql.append(" WHERE id = ? ");

            ps = c.prepareStatement(sql.toString());
            DAOUtils.setParameters(ps, id);
            rs = ps.executeQuery();

            TipoTicket tt = null;
            if (rs.next()) {
                tt = loadNext(rs);
            }
            return tt;

        } catch (SQLException e) {
        	logger.error("Error en TipoTicketDAO.findById con ID {}: {}", id, e.getMessage());
		    throw new DataException(e); 
        } finally {
        	JDBCUtils.close(rs, ps);
        }
    }

    public List<TipoTicket> findAll(Connection c) throws DataException {
        List<TipoTicket> resultados = new ArrayList<>();
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
        	logger.error("Error en TipoTicketDAO.findAll: {}", e.getMessage());
		    throw new DataException(e); 
        } finally {
        	JDBCUtils.close(rs, ps);
        }
    }

    private TipoTicket loadNext(ResultSet rs) throws SQLException {
        int i = 1;
        TipoTicket tt = new TipoTicket();
        tt.setId(rs.getLong(i++));
        tt.setNombre(rs.getString(i++));
        return tt;
    }
}
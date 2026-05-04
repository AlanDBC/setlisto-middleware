package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.TipoTicket;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class TipoTicketDAO {

    private static final String BASE_QUERY = " SELECT id, name FROM ticket_type ";

    public TipoTicketDAO() {
    }

    /**
     * Recupera un tipo de ticket específico por su ID
     */
    public TipoTicket findById(Long id) {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = JDBCUtils.getConnection();
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

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAOUtils.close(rs, ps, c);
        }
        return null;
    }

    /**
     * Obtiene todos los tipos de tickets registrados en la tabla maestra.
     */
    public List<TipoTicket> findAll() {
        List<TipoTicket> resultados = new ArrayList<>();
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            c = JDBCUtils.getConnection();
            ps = c.prepareStatement(BASE_QUERY);
            rs = ps.executeQuery();

            while (rs.next()) {
                resultados.add(loadNext(rs));
            }
            return resultados;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAOUtils.close(rs, ps, c);
        }
        return resultados;
    }

    /**
     * Mapea el ResultSet al objeto TipoTicket.
     */
    private TipoTicket loadNext(ResultSet rs) throws Exception {
        int i = 1;
        TipoTicket tt = new TipoTicket();
        tt.setId(rs.getLong(i++));
        tt.setNombre(rs.getString(i++));
        return tt;
    }
}
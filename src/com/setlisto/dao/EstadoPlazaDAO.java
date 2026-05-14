package com.setlisto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.setlisto.model.EstadoPlaza;
import com.setlisto.utils.DAOUtils;
import com.setlisto.utils.JDBCUtils;

public class EstadoPlazaDAO {

    // Consulta base a la tabla seat_status definida en el schema [1]
    private static final String BASE_QUERY = " SELECT id, name FROM seat_status ";

    public EstadoPlazaDAO() {
    }

    /**
     * Busca un estado de plaza por su ID (1=AVAILABLE, 2=SOLD, 3=DISABLED)
     */
    public EstadoPlaza findById(Connection c, Long id)  throws Exception {
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
        } catch (Exception e) {
        	throw e;
        } finally {
        	JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Recupera todos los estados de plaza disponibles.
     */
    public List<EstadoPlaza> findAll(Connection c) throws Exception {
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
        } catch (Exception e) {
        	throw e;
        } finally {
        	JDBCUtils.close(rs, ps);
        }
    }

    /**
     * Mapea el ResultSet al objeto de modelo EstadoPlaza.
     * Nota: Se usa ep.setEstado() porque así está definido en tu POJO [2].
     */
    private EstadoPlaza loadNext(ResultSet rs) throws Exception {
        int i = 1;
        EstadoPlaza ep = new EstadoPlaza();
        ep.setId(rs.getLong(i++));
        ep.setNombre(rs.getString(i++));
        return ep;
    }
}
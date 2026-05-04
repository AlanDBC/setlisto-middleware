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
    public EstadoPlaza findById(Long id) {
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

            EstadoPlaza ep = null;
            if (rs.next()) {
                ep = loadNext(rs);
            }
            return ep;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DAOUtils.close(rs, ps, c);
        }
        return null;
    }

    /**
     * Recupera todos los estados de plaza disponibles.
     */
    public List<EstadoPlaza> findAll() {
        List<EstadoPlaza> resultados = new ArrayList<>();
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
     * Mapea el ResultSet al objeto de modelo EstadoPlaza.
     * Nota: Se usa ep.setEstado() porque así está definido en tu POJO [2].
     */
    private EstadoPlaza loadNext(ResultSet rs) throws Exception {
        int i = 1;
        EstadoPlaza ep = new EstadoPlaza();
        ep.setId(rs.getLong(i++));
        ep.setNombre(rs.getString(i++)); // Mapea la columna 'name' al atributo 'estado' [2]
        return ep;
    }
}
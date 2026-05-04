package com.setlisto.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class DAOUtils {
	
	public static Connection getConnection() throws SQLException {
		return JDBCUtils.getConnection();
	}

	public static void setParameters(PreparedStatement ps, List<Object> params) throws SQLException {
	    int i = 1;
	    for (Object param : params) {
	        ps.setObject(i++, param);
	    }
	}
	// Método nuevo que acepta argumentos sueltos (...)
	public static void setParameters(PreparedStatement ps, Object... params) throws SQLException {
	    setParameters(ps, Arrays.asList(params)); // Reutiliza tu método original
	}
	
	public static void close(ResultSet rs, PreparedStatement ps, Connection c) {
		try { if (rs != null) rs.close(); } catch (Exception ignored) {}
		try { if (ps != null) ps.close(); } catch (Exception ignored) {}
		try { if (c != null) c.close(); } catch (Exception ignored) {}
	}	
}
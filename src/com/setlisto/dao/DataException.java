package com.setlisto.dao;

import java.sql.SQLException;

import com.setlisto.model.SetlistoException;

public class DataException extends SetlistoException {

    // Constructor que recibe solo la SQLException
    public DataException(SQLException cause) {
        super(cause);
    }

    // Constructor que recibe un mensaje personalizado y la SQLException
    public DataException(String message, SQLException cause) {
        super(message, cause);
    }

    // Constructor para mensajes de error lógicos de datos sin SQLException
    public DataException(String message) {
        super(message);
    }

    // Constructor que recibe otra DataException, por si hay llamado entre DAOs
    public DataException(DataException cause) {
        super(cause);
    }
    
}

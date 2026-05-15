package com.setlisto.dao;

import com.setlisto.model.SetlistoException;

public class DataException extends SetlistoException {

	public DataException() {
	}

	public DataException(String message) {
		super(message);
	}

	public DataException(String message, Throwable cause) {
		super(message, cause);
	}

}

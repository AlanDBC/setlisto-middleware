package com.setlisto.model;


public class SetlistoException extends Exception {

	public SetlistoException(String message) {
		super(message); 
	}
	
	public SetlistoException(Throwable cause) { 
		super(cause); 
	}
	
	public SetlistoException(String message, Throwable cause) { 
		super(message, cause); 
	}
	
}
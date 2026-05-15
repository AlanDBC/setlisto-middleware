package com.setlisto.service;

public class MailException extends ServiceException{

	public MailException() {
	}
	
	public MailException(String message) {
		super(message);
	}
	
	public MailException(String message, Throwable cause) {
		super(message, cause);
	}
}

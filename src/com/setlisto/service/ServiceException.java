package com.setlisto.service;

import com.setlisto.model.SetlistoException;

public class ServiceException extends SetlistoException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}

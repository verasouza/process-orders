package com.vsouza.processorders.controllers.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceNotFoundException(Object id){
        super("Resource not found. Id " + id);
    }

    @Override
    public String toString() {
        return "NotFoundException: " + getMessage();
    }
}
package com.shopbee.cartservice.shared.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class CartServiceException extends WebApplicationException {

    public CartServiceException(String message, int status) {
        super(message, status);
    }

    public CartServiceException(String message, Response.Status status) {
        super(message, status);
    }
}

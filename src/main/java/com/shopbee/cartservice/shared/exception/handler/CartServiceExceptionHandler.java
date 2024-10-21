package com.shopbee.cartservice.shared.exception.handler;

import com.shopbee.cartservice.shared.exception.CartServiceException;
import com.shopbee.cartservice.shared.exception.ErrorResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CartServiceExceptionHandler implements ExceptionMapper<CartServiceException> {

    @Override
    public Response toResponse(CartServiceException e) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(e.getMessage());
        return Response.status(e.getResponse().getStatus()).entity(errorResponse).type(MediaType.APPLICATION_JSON).build();
    }
}

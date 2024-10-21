package com.shopbee.cartservice.api;

import com.shopbee.cartservice.dto.AddCartItemRequest;
import com.shopbee.cartservice.dto.UpdateQuantityRequest;
import com.shopbee.cartservice.service.CartService;
import com.shopbee.cartservice.shared.page.PageRequest;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("carts")
@Produces(MediaType.APPLICATION_JSON)
public class CartResource {

    private final CartService cartService;

    @Inject
    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @GET
    @Authenticated
    public Response viewCart(@Valid PageRequest pageRequest) {
        return Response.ok(this.cartService.getCurrent(pageRequest)).build();
    }

    @DELETE
    @Authenticated
    public Response clear() {
        cartService.clear();
        return Response.noContent().build();
    }

    @POST
    @Path("add")
    @Authenticated
    public Response addItemsToCart(@Valid AddCartItemRequest addCartItemRequest,
                                   @Context UriInfo uriInfo) {
        return Response.created(uriInfo.getAbsolutePath()).entity(cartService.add(addCartItemRequest)).build();
    }

    @PUT
    @Path("{id}")
    @Authenticated
    public Response updateQuantity(@PathParam("id") Long id, @Valid UpdateQuantityRequest updateQuantityRequest) {
        cartService.updateQuantity(id, updateQuantityRequest);
        return Response.ok().build();
    }

    @DELETE
    @Path("{id}")
    @Authenticated
    public Response removeCart(@PathParam("id") Long id) {
        cartService.remove(id);
        return Response.noContent().build();
    }

}

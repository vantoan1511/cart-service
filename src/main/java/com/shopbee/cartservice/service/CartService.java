package com.shopbee.cartservice.service;

import com.shopbee.cartservice.dto.AddCartItemRequest;
import com.shopbee.cartservice.dto.UpdateQuantityRequest;
import com.shopbee.cartservice.entity.Cart;
import com.shopbee.cartservice.external.product.Product;
import com.shopbee.cartservice.external.product.ProductServiceClient;
import com.shopbee.cartservice.repository.CartRepository;
import com.shopbee.cartservice.shared.exception.CartServiceException;
import com.shopbee.cartservice.shared.page.PageRequest;
import com.shopbee.cartservice.shared.page.PagedResponse;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@ApplicationScoped
public class CartService {

    private final ProductServiceClient productServiceClient;
    private final CartRepository cartRepository;
    private final SecurityIdentity identity;

    @Inject
    public CartService(@RestClient ProductServiceClient productServiceClient,
                       CartRepository cartRepository,
                       SecurityIdentity identity) {
        this.productServiceClient = productServiceClient;
        this.cartRepository = cartRepository;
        this.identity = identity;
    }

    public PagedResponse<Cart> getCurrent(PageRequest pageRequest) {
        String username = getCurrentUsername();
        List<Cart> cartItems = cartRepository.findByUsername(username, pageRequest);
        long totalItems = cartRepository.getTotal(username);
        return PagedResponse.of((int) totalItems, pageRequest, cartItems);
    }

    @Transactional
    public Cart add(AddCartItemRequest addCartItemRequest) {
        Product product = productServiceClient.getBySlug(addCartItemRequest.getProductSlug());
        if (addCartItemRequest.getQuantity() > product.getStockQuantity()) {
            throw new CartServiceException("Request quantity must be less than or equal stock", Response.Status.BAD_REQUEST);
        }

        Cart cart = new Cart();
        cart.setQuantity(addCartItemRequest.getQuantity());
        cart.setProductSlug(addCartItemRequest.getProductSlug());
        cart.setUsername(getCurrentUsername());
        cartRepository.persist(cart);
        return cart;
    }

    @Transactional
    public void updateQuantity(Long id, UpdateQuantityRequest updateQuantityRequest) {
        Cart cart = getById(id);

        verifyPermission(cart);

        Product product = productServiceClient.getBySlug(cart.getProductSlug());
        if (updateQuantityRequest.getQuantity() > product.getStockQuantity()) {
            throw new CartServiceException("Request quantity must be less than or equal stock", Response.Status.BAD_REQUEST);
        }

        cart.setQuantity(updateQuantityRequest.getQuantity());
    }

    @Transactional
    public void remove(Long id) {
        Cart cart = getById(id);
        verifyPermission(cart);
        cartRepository.deleteById(id);
    }

    @Transactional
    public void clear() {
        cartRepository.deleteByUsername(getCurrentUsername());
    }

    private void verifyPermission(Cart cart) {
        if (!cart.getUsername().equals(getCurrentUsername())) {
            throw new CartServiceException("You do not have permission", Response.Status.FORBIDDEN);
        }
    }

    private Cart getById(Long id) {
        return cartRepository.findByIdOptional(id)
                .orElseThrow(() -> new CartServiceException("Cart not found", Response.Status.BAD_REQUEST));
    }

    private String getCurrentUsername() {
        return identity.getPrincipal().getName();
    }
}

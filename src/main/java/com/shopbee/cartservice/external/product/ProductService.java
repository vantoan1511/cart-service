package com.shopbee.cartservice.external.product;

import com.shopbee.cartservice.external.product.dto.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Slf4j
@ApplicationScoped
public class ProductService {

    private final ProductServiceClient productServiceClient;

    @Inject
    public ProductService(@RestClient ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    public Product getBySlug(String productSlug) {
        try {
            return productServiceClient.getBySlug(productSlug);
        } catch (Exception e) {
            log.warn("Failed to get product by slug {}", productSlug);
            return null;
        }
    }
}

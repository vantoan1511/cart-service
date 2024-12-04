package com.shopbee.cartservice.external.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal basePrice;
    private BigDecimal salePrice;
    private Integer stockQuantity;
    private List<ProductImage> images;
}

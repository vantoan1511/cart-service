package com.shopbee.cartservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {

    @Min(value = 0, message = "Quantity must be greater than 0")
    private int quantity;

    @NotBlank(message = "ProductSlug must not be null")
    private String productSlug;

}

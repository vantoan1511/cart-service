package com.shopbee.cartservice.shared.page;

import jakarta.validation.constraints.Min;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    @QueryParam("page")
    @DefaultValue("1")
    @Min(value = 1, message = "Page number must be equal or greater than 1")
    private int page;

    @QueryParam("size")
    @DefaultValue("20")
    @Min(value = 0, message = "Page size must be equal or greater than 0")
    private int size;

}

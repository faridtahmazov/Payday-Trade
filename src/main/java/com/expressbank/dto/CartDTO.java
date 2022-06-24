package com.expressbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartDTO {
    List<CartItemDTO> cartItemDTOList;
    private Double totalCost;

    public CartDTO() {
    }
}

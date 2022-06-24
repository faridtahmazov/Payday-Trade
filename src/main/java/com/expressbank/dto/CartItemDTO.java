package com.expressbank.dto;

import com.expressbank.model.Cart;
import com.expressbank.model.StockData;
import com.expressbank.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CartItemDTO {
    private Integer id;
    private Integer quantity;
    private StockData stockData;

    public CartItemDTO() {
    }

    public CartItemDTO(Cart cart){
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.stockData = cart.getStockData();
    }
}

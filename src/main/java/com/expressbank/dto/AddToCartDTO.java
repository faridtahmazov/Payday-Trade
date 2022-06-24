package com.expressbank.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AddToCartDTO {
    private Integer id;
    private @NotNull Integer stockId;
    private @NotNull Integer quantity;

    public AddToCartDTO() {
    }


}

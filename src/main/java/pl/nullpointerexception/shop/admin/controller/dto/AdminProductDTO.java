package pl.nullpointerexception.shop.admin.controller.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class AdminProductDTO {

    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private String currency;

}
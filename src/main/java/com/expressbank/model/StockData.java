package com.expressbank.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
@Entity
public class StockData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String company;
    @Lob
    @Column(length = 65555)
    private String description;
    private Double lastPrice;
    private String symbol;

    public StockData() {
    }

    public StockData(String company, String description, Double lastPrice, String symbol) {
        this.company = company;
        this.description = description;
        this.lastPrice = lastPrice;
        this.symbol = symbol;
    }
}

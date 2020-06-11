package br.com.guilhermealvessilve.data;

import lombok.Data;

import javax.json.bind.annotation.JsonbProperty;

@Data
public class Currency {

    private String id;

    private String symbol;

    private String name;

    @JsonbProperty("price_usd")
    private String priceUsd;

    @JsonbProperty("price_btc")
    private String priceBtc;
}

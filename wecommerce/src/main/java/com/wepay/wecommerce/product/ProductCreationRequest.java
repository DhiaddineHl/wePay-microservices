package com.wepay.wecommerce.product;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
public class ProductCreationRequest {

    private final String name;
    private final String description;
    private final Float price;
    private final String image_url;

    @JsonCreator
    public ProductCreationRequest(
            @JsonProperty("name") String name,
            @JsonProperty("description") String description,
            @JsonProperty("price") Float price,
            @JsonProperty("image_url") String image_url) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image_url = image_url;
    }
}

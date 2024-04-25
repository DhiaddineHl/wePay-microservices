package com.wepay.wecommerce.product;


import lombok.Data;

@Data
public class ProductCreationRequest {

    private String name;
    private String description;
    private Float price;
    private String image_url;

}

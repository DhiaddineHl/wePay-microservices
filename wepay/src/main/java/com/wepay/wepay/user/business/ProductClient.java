package com.wepay.wepay.user.business;


import com.wepay.wepay.product.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service", url = "${spring.application.configuration.products-url}")
public interface ProductClient {

    @GetMapping("/seller/{seller_id}")
    List<Product> findAllProductsBySeller(
        @PathVariable("seller_id") Integer seller_id
    );

}

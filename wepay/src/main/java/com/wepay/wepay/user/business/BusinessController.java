package com.wepay.wepay.user.business;


import com.wepay.wepay.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seller")
public class BusinessController {

    private final BusinessUserService service;

    @GetMapping("/with-products")
    public ResponseEntity<List<Product>> getSellerProducts(
        @RequestHeader("Authorization") String authHeader
    ){
        return ResponseEntity.ok(service.getSellerProducts(authHeader));
    }

}

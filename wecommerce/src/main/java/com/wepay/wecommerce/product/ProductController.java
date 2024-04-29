package com.wepay.wecommerce.product;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<Product> getProductById(
            @PathVariable("product_id") Integer product_id
    ){
        return ResponseEntity.ok(productService.getProductById(product_id));
    }

    @GetMapping("/seller/{seller_id}")
    public ResponseEntity<List<Product>> getProductsBySeller(
            @PathVariable("seller_id") Integer seller_id
    ){
        return ResponseEntity.ok(productService.getProductsBySeller(seller_id));
    }

    @PostMapping
    public void createProduct(
            @RequestBody ProductCreationRequest request,
            @RequestHeader("Authorization") String user_token
    ){
        productService.createProduct(request, user_token);
    }

}

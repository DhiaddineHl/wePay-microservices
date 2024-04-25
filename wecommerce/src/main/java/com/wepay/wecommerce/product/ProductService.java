package com.wepay.wecommerce.product;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public List<Product> getAllProducts (){
        return repository.findAll();
    }

    public Product getProductById(Integer id){
        return repository.getReferenceById(id);
    }

    public List<Product> getProductsBySeller(
            Integer seller_id
    ){
        return null;
    }

    public void createProduct(
            ProductCreationRequest request,
            String user_token
    ){
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image_url(request.getImage_url())
                .build();

        repository.save(product);
    }

}
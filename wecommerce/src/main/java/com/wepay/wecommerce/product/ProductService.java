package com.wepay.wecommerce.product;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final UserClient client;

    public List<Product> getAllProducts (){
        return repository.findAll();
    }

    public Product getProductById(Integer id){
        return repository.getReferenceById(id);
    }

    public List<Product> getProductsBySeller(
            Integer seller_id
    ){
        return repository.findAllBySellerId(seller_id);
    }

    public void createProduct(
            ProductCreationRequest request,
            String user_token
    ){
        Integer sellerId = client.findSellerByToken(user_token);

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .image_url(request.getImage_url())
                .sellerId(sellerId)
                .build();

        repository.save(product);
    }

    public List<Product> getProductsBySellerToken(String user_token) {
        Integer sellerId = client.findSellerByToken(user_token);

        return repository.findAllBySellerId(sellerId);

    }
}

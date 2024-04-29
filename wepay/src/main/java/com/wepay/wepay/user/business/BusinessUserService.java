package com.wepay.wepay.user.business;


import com.wepay.wepay.product.Product;
import com.wepay.wepay.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessUserService {

    private final TokenRepository tokenRepository;
    private ProductClient client;
    public List<Product> getSellerProducts(String authHeader) {

        String sellerToken = authHeader.substring(7);
        var seller = tokenRepository.findByToken(sellerToken)
                .orElseThrow()
                .getUser();
        return client.findAllProductsBySeller(seller.getId());
    }
}

package com.wepay.wecommerce.product;

import com.wepay.wecommerce.seller.Seller;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${spring.application.configuration.user-url}")
public interface UserClient {

    @GetMapping("/byToken")
    Integer findSellerByToken(
        @RequestHeader("Authorization") String authHeader
    );

}

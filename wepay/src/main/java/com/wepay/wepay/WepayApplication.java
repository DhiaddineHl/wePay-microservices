package com.wepay.wepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WepayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WepayApplication.class, args);
	}

}

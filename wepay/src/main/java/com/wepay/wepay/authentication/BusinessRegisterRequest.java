package com.wepay.wepay.authentication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String business_name;
    private String tax_registration_number;

}

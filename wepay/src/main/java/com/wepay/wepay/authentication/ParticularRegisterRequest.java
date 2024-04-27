package com.wepay.wepay.authentication;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticularRegisterRequest {

    private String name;
    private String email;
    private String password;
    private String ICN;


}

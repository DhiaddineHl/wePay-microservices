package com.wepay.wepay.payment.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreationRequest {

    Integer beneficiaryId;
    Float amount;

}

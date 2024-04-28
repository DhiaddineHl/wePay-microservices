package com.wepay.wepay.payment.dtos;


import com.wepay.wepay.payment.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentExecutionResponse {

    private PaymentStatus status;
    private String responseMessage;

}

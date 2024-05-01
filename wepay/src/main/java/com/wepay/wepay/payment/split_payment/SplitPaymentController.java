package com.wepay.wepay.payment.split_payment;


import com.wepay.wepay.payment.dtos.CollaboratorsAddingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/split-payment")
public class SplitPaymentController {

    private final SplitPaymentService service;

    @PostMapping("/execute/{payment-id}")
    public void executeSplitPayment(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CollaboratorsAddingRequest request,
            @PathVariable("payment-id") Integer paymentId
            ){
        service.executeSplitPayment(
                authHeader,
                paymentId,
                request
        );
    }

}

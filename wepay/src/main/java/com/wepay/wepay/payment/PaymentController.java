package com.wepay.wepay.payment;


import com.wepay.wepay.payment.dtos.PaymentCreationRequest;
import com.wepay.wepay.payment.dtos.ClassicPaymentExecutionRequest;
import com.wepay.wepay.payment.dtos.PaymentExecutionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/classic/create")
    public ResponseEntity<Integer> createPayment(
            @RequestBody PaymentCreationRequest request)
    {
        return ResponseEntity.ok(paymentService.createClassicPayment(request));
    }

    @PostMapping("/classic/execute")
    public ResponseEntity<PaymentExecutionResponse> executePayment(
            @RequestBody ClassicPaymentExecutionRequest request,
            @RequestHeader("Authorization") String authHeader
            )
    {
        return ResponseEntity.ok(paymentService.executeClassicPayment(request, authHeader));
    }

}

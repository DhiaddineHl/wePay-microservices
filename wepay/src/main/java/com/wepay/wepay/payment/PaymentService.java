package com.wepay.wepay.payment;


import com.wepay.wepay.payment.dtos.ClassicPaymentCreationRequest;
import com.wepay.wepay.payment.dtos.ClassicPaymentExecutionRequest;
import com.wepay.wepay.payment.dtos.PaymentExecutionResponse;
import com.wepay.wepay.token.TokenRepository;
import com.wepay.wepay.user.AccountType;
import com.wepay.wepay.user.AppUser;
import com.wepay.wepay.user.UserRepository;
import com.wepay.wepay.user.business.BusinessUser;
import com.wepay.wepay.user.business.BusinessUserRepository;
import com.wepay.wepay.user.particular.ParticularUser;
import com.wepay.wepay.user.particular.ParticularUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BusinessUserRepository businessUserRepository;
    private final ParticularUserRepository particularUserRepository;
    private final PaymentRepository paymentRepository;
    private final TokenRepository tokenRepository;

    public boolean isAbleToPay(ParticularUser payer, Float amount){
        return payer.getBalance() > amount;
    }

    public void updateBalance(Payment payment){
        ParticularUser payer = payment.getPayer();
        BusinessUser beneficiary = payment.getBeneficiary();

        payer.setBalance(payer.getBalance() - payment.getTotal());
        beneficiary.setBalance(beneficiary.getBalance() - payment.getTotal());

        particularUserRepository.save(payer);
        businessUserRepository.save(beneficiary);
    }
    public Integer createClassicPayment(ClassicPaymentCreationRequest request) {

        var beneficiary = businessUserRepository.findById(request.getBeneficiaryId())
                .orElseThrow();

        Payment payment = Payment.builder()
                .beneficiary(beneficiary)
                .total(request.getAmount())
                .paymentMethod(PaymentMethod.CLASSIC)
                .paymentStatus(PaymentStatus.PENDING)
                .timeStamp(
                        new SimpleDateFormat("dd MMMM yyyy").format(new Date())
                )
                .build();

        paymentRepository.save(payment);

        return payment.getId();
    }

    public PaymentExecutionResponse executeClassicPayment(
            ClassicPaymentExecutionRequest request,
            String authHeader
            ) {

        var payment = paymentRepository.findById(request.getPayment_id()).orElseThrow();
        String token = authHeader.substring(7);

        AppUser user = tokenRepository.findByToken(token)
                .orElseThrow()
                .getUser();

        ParticularUser payer = particularUserRepository.getReferenceById(user.getId());
        if (!isAbleToPay(payer, payment.getTotal())){
            payment.setPaymentStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);

            return PaymentExecutionResponse.builder()
                    .responseMessage("Error! You don't have enough balance to complete this payment")
                    .status(PaymentStatus.FAILED)
                    .build();
        }

        payment.setPayer(payer);
        payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
        paymentRepository.save(payment);

        updateBalance(payment);

        return PaymentExecutionResponse.builder()
                .status(PaymentStatus.SUCCEEDED)
                .responseMessage("Payment completed successfully!")
                .build();
    }
}

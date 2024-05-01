package com.wepay.wepay.payment.split_payment;


import com.wepay.wepay.payment.PaymentMethod;
import com.wepay.wepay.payment.PaymentRepository;
import com.wepay.wepay.payment.PaymentService;

import com.wepay.wepay.payment.PaymentStatus;
import com.wepay.wepay.payment.dtos.CollaboratorInfos;
import com.wepay.wepay.payment.dtos.CollaboratorsAddingRequest;
import com.wepay.wepay.token.TokenRepository;
import com.wepay.wepay.user.AppUser;
import com.wepay.wepay.user.UserRepository;
import com.wepay.wepay.user.particular.ParticularUser;
import com.wepay.wepay.user.particular.ParticularUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SplitPaymentService {

    private final PaymentService service;
    private final PaymentRepository paymentRepository;
    private final TokenRepository tokenRepository;
    private final ParticularUserRepository particularUserRepository;
    private final ParticularUserRepository userRepository;


    public void updateBalanceForSplitPayment(
            ParticularUser payer,
            Float amount
    ){
        payer.setBalance(payer.getBalance() - amount);
    }


    public void executeSplitPayment(
            String authHeader,
            Integer paymentId,
            CollaboratorsAddingRequest request) {
        //find the payment
        var payment = paymentRepository.findById(paymentId).orElseThrow();

        //find the user by token
        String token = authHeader.substring(7);
        AppUser user = tokenRepository.findByToken(token)
                .orElseThrow()
                .getUser();

        //find the principal payer
        ParticularUser principalPayer = particularUserRepository.getReferenceById(user.getId());

        //check weather the principal payer is able to pay his percentage
        Float principalPayerAmount = (payment.getTotal() * request.getPrincipalPayerPercentage()) / 100;
        if (!service.isAbleToPay(principalPayer, principalPayerAmount)){
            throw new IllegalStateException("You are not able to proceed this payment!");
        }

        //check weather the collaborators are able to pay their percentages
        for (
                CollaboratorInfos info : request.getCollaboratorInfos()
        ){
            var collaborator = userRepository.findByEmail(info.getCollaboratorEmail())
                    .orElseThrow();
            if (!service.isAbleToPay(
                    collaborator,
                    (payment.getTotal() * info.getCollaboratorPercentage()) / 100
            )){
                throw new IllegalStateException("One of the collaborators is not able to proceed this payment !");
            }
        }

        //update the payers balances
        updateBalanceForSplitPayment(
                principalPayer,
                principalPayerAmount
        );
        for (
                CollaboratorInfos info : request.getCollaboratorInfos()
        ){
            var collaborator = userRepository.findByEmail(info.getCollaboratorEmail())
                    .orElseThrow();
            updateBalanceForSplitPayment(
                    collaborator,
                    (info.getCollaboratorPercentage() * payment.getTotal()) /100
            );
        }

        //update and save the payment details

        payment.setPaymentStatus(PaymentStatus.SUCCEEDED);
        payment.setPayer(principalPayer);
        payment.setPaymentMethod(PaymentMethod.SPLIT);

        paymentRepository.save(payment);
    }
}

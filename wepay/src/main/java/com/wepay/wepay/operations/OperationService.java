package com.wepay.wepay.operations;


import com.wepay.wepay.token.TokenRepository;
import com.wepay.wepay.user.AppUser;
import com.wepay.wepay.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;


    public void updateBalance(AppUser sender, AppUser receiver, Float amount){

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);
    }

    public void sendBalance(String authHeader, OperationRequest request) {
        var sender = tokenRepository.findByToken(authHeader.substring(7))
                .orElseThrow()
                .getUser();
        var receiver = userRepository.findByEmail(request.getCollaboratorEmail())
                .orElseThrow();

        updateBalance(sender, receiver, request.getOperatedBalance());

    }

    public void requestBalance(String authHeader, OperationRequest request) {

        var requester = tokenRepository.findByToken(authHeader.substring(7))
                .orElseThrow()
                .getUser();

        var sender = userRepository.findByEmail(request.getCollaboratorEmail())
                .orElseThrow();

        Operation operation = Operation.builder()
                .type(OperationType.RECEIVE)
                .build();


    }

    public void confirmRequest(String authHeader, Integer requestId) {

    }
}

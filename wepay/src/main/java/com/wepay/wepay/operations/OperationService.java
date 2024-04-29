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
    private final OperationRepository operationRepository;


    public void updateBalance(AppUser sender, AppUser receiver, Float amount){

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);
    }
    public boolean isAbleToSend(AppUser requestedUser, Float amount){
        return requestedUser.getBalance() > amount;
    }

    public void sendBalance(String authHeader, OperationRequest request) {
        var sender = tokenRepository.findByToken(authHeader.substring(7))
                .orElseThrow()
                .getUser();
        var receiver = userRepository.findByEmail(request.getCollaboratorEmail())
                .orElseThrow();

        if (!isAbleToSend(sender, request.getOperatedBalance())){
            throw new IllegalStateException("You have no enough balance");
        }

        updateBalance(sender, receiver, request.getOperatedBalance());

        operationRepository.save(
                Operation.builder()
                        .operatedBalance(request.getOperatedBalance())
                        .receiver(receiver)
                        .sender(sender)
                        .type(OperationType.SEND)
                        .status(OperationStatus.COMPLETED)
                        .build()
        );

    }

    public void requestBalance(String authHeader, OperationRequest request) {

        var requester = tokenRepository.findByToken(authHeader.substring(7))
                .orElseThrow()
                .getUser();

        var sender = userRepository.findByEmail(request.getCollaboratorEmail())
                .orElseThrow();

        Operation operation = Operation.builder()
                .type(OperationType.RECEIVE)
                .sender(sender)
                .receiver(requester)
                .operatedBalance(request.getOperatedBalance())
                .status(OperationStatus.PENDING)
                .build();

        operationRepository.save(operation);

    }

    public void confirmRequest(String authHeader, Integer requestId) {

        var requestOperation = operationRepository.findById(requestId)
                .orElseThrow();

        var requestedUser = tokenRepository.findByToken(authHeader.substring(7))
                .orElseThrow()
                .getUser();

        if (requestOperation.getSender() != requestedUser){
            //todo : handle the exception
            throw new IllegalStateException("");
        }

        if (!isAbleToSend(requestedUser, requestOperation.getOperatedBalance())){
            throw new IllegalStateException("You are not able to send this amount !");
        }

        updateBalance(requestedUser,
                requestOperation.getReceiver(),
                requestOperation.getOperatedBalance());

        requestOperation.setStatus(OperationStatus.COMPLETED);
        operationRepository.save(requestOperation);

    }

    public void declineRequest(Integer requestId){

        var requestOperation = operationRepository.findById(requestId)
                .orElseThrow();

        requestOperation.setStatus(OperationStatus.FAILED);
        operationRepository.save(requestOperation);
    }
}

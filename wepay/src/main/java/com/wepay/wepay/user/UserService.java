package com.wepay.wepay.user;


import com.wepay.wepay.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TokenRepository tokenRepository;
    public Integer getUserIdByToken(String authHeader) {

        String token = authHeader.substring(7);

        return tokenRepository.findByToken(token)
                .orElseThrow()
                .getUser()
                .getId();

    }

    public Float getUserBalance(String authHeader) {
        String token = authHeader.substring(7);

        return tokenRepository.findByToken(token)
                .orElseThrow()
                .getUser()
                .getBalance();
    }
}

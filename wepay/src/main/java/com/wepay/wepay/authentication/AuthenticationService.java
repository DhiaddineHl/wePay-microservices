package com.wepay.wepay.authentication;


import com.wepay.wepay.token.JwtService;
import com.wepay.wepay.token.Token;
import com.wepay.wepay.token.TokenRepository;
import com.wepay.wepay.token.TokenType;
import com.wepay.wepay.user.AccountType;
import com.wepay.wepay.user.AppUser;
import com.wepay.wepay.user.UserRepository;
import com.wepay.wepay.user.business.BusinessUser;
import com.wepay.wepay.user.particular.ParticularUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public void verifyUser(String user_email){
        Optional<AppUser> user =  repository.findByEmail(user_email);
        if (user.isPresent()){
          throw new IllegalStateException("Email already taken");
        }
    }

    public AuthResponse registerParticular(ParticularRegisterRequest request) {

        verifyUser(request.getEmail());

        var particularUser = ParticularUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountType(AccountType.PARTICULAR)
                .balance(100F)
                .ICN(request.getICN())
                .build();
        var savedUser = repository.save(particularUser);
        var jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser,jwtToken);

        return AuthResponse.builder()
                .access_token(jwtToken)
                .build();

    }

    public AuthResponse registerBusiness(BusinessRegisterRequest request) {

        verifyUser(request.getEmail());

        var businessUser = BusinessUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountType(AccountType.PARTICULAR)
                .balance(100F)
                .tax_registration_number(request.getTax_registration_number())
                .business_name(request.getBusiness_name())
                .build();

        var savedUser = repository.save(businessUser);
        var jwtToken = jwtService.generateToken(savedUser);
        saveUserToken(savedUser,jwtToken);

        return AuthResponse.builder()
                .access_token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(()-> new BadCredentialsException("Invalid email or password"));

        var jwtToken = jwtService.generateToken(user);
        saveUserToken(user, jwtToken);

        return AuthResponse.builder()
                .access_token(jwtToken)
                .build();
    }

    public void saveUserToken(AppUser appUser, String jwtToken) {
        var token = Token.builder()
                .user(appUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}

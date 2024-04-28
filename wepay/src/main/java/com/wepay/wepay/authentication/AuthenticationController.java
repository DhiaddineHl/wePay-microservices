package com.wepay.wepay.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {


    private final AuthenticationService authService;

    @PostMapping("/register-particular")
    public ResponseEntity<AuthResponse> registerParticular(
            @RequestBody ParticularRegisterRequest request
    ) {
        return ResponseEntity.ok(authService.registerParticular(request));
    };
    @PostMapping("/register-business")
    public ResponseEntity<AuthResponse> registerBusiness(
            @RequestBody BusinessRegisterRequest request
    ) {
        return ResponseEntity.ok(authService.registerBusiness(request));
    };

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest authenticationRequest
    ){
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    };


}

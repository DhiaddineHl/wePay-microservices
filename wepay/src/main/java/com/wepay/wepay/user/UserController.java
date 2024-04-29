package com.wepay.wepay.user;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/byToken")
    public ResponseEntity<AppUser> getUserByToken(
            @RequestHeader("Authorization") String authHeader
    ){
        return ResponseEntity.ok(userService.getUserByToken(authHeader));
    }

}

package com.wepay.wepay.operations;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operations")
public class OperationController {

    private final OperationService service;

    @PostMapping("/send")
    public void sendBalance(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OperationRequest request
    ){
        service.sendBalance(authHeader, request);
    }

    @PostMapping("/request")
    public void requestBalance(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody OperationRequest request
    ){
        service.requestBalance(authHeader, request);
    }

    @PutMapping("/confirm-request/{requestId}")
    public void requestBalance(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable("requestId") Integer requestId
    ){
        service.confirmRequest(authHeader, requestId);
    }

    @PutMapping("/decline-request/{requestId}")
    public void declineRequest(
            @PathVariable("requestId") Integer requestId
    ){
        service.declineRequest(requestId);
    }

}

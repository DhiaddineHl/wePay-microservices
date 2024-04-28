package com.wepay.wepay.operations;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationRequest {

    private String collaboratorEmail;
    private Float operatedBalance;

}

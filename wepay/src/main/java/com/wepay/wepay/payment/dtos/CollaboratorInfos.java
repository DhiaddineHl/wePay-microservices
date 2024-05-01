package com.wepay.wepay.payment.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@AllArgsConstructor
public class CollaboratorInfos {

    private final String collaboratorEmail;
    private final Float collaboratorPercentage;

}

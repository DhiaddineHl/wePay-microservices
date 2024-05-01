package com.wepay.wepay.payment.dtos;


import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorsAddingRequest {

    private Float principalPayerPercentage;
    private List<CollaboratorInfos> collaboratorInfos;

}

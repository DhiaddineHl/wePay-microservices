package com.wepay.wepay.payment.dtos;

import com.wepay.wepay.user.particular.ParticularUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaboratorInfos {

    private ParticularUser collaborator;
    private Float collaboratorPercentage;
    private boolean isCollaboratorConfirmed;

}

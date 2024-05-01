package com.wepay.wepay.payment.split_payment;

import com.wepay.wepay.payment.Payment;
import com.wepay.wepay.payment.dtos.CollaboratorInfos;
import com.wepay.wepay.user.particular.ParticularUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class SplitPayment extends Payment {

    private Float principalPayerPercentage;
    private List<CollaboratorInfos> collaboratorInfos;

}

package com.wepay.wepay.payment;


import com.wepay.wepay.user.business.BusinessUser;
import com.wepay.wepay.user.particular.ParticularUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "beneficiary_id")
    private BusinessUser beneficiary;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private ParticularUser payer;

    private String timeStamp;
    private Float total;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;

}

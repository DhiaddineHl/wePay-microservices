package com.wepay.wepay.user.business;

import com.wepay.wepay.user.AppUser;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class BusinessUser extends AppUser {

    private String business_name;
    private String tax_registration_number;

}

package com.wepay.wepay.operations;


import com.wepay.wepay.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private OperationType type;
    private Float operatedBalance;
    private OperationStatus status;

    @ManyToOne
    private AppUser sender;
    @ManyToOne
    private AppUser receiver;

}

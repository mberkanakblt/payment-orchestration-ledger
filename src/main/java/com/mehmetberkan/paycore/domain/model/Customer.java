package com.mehmetberkan.paycore.domain.model;

import com.mehmetberkan.paycore.domain.enums.AccountStatus;
import com.mehmetberkan.paycore.domain.enums.KycLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tblcustomer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private KycLevel kycLevel = KycLevel.LEVEL_0;

    private AccountStatus status;

}

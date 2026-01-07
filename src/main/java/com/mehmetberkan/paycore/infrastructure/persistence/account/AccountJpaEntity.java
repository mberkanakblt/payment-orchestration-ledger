package com.mehmetberkan.paycore.infrastructure.persistence.account;

import com.mehmetberkan.paycore.domain.enums.AccountStatus;
import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class AccountJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iban;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    private Money balance;

    @Version
    private Long version;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;
}

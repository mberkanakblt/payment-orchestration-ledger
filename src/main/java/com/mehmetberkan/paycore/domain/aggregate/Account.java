package com.mehmetberkan.paycore.domain.aggregate;

import com.mehmetberkan.paycore.domain.enums.AccountStatus;
import com.mehmetberkan.paycore.domain.enums.Currency;
import com.mehmetberkan.paycore.domain.exception.DomainException;
import com.mehmetberkan.paycore.domain.exception.InsufficientFundsException;
import com.mehmetberkan.paycore.domain.valueobject.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Account {
    private Long id;
    private String iban;
    private Currency currency;
    private Money balance;
    private AccountStatus status;
    private Long version;


    public void debit(Money amount) {
        validateActive();
        validateSameCurrency(amount);

        if (balance.isLessThan(amount)) {
            throw new InsufficientFundsException("Insufficient balance in account: " + iban);
        }
        this.balance = balance.subtract(amount);
    }

    public void credit(Money amount) {
        validateActive();
        validateSameCurrency(amount);
        this.balance = balance.add(amount);
    }

    private void validateActive() {
        if (status != AccountStatus.ACTIVE) {
            throw new DomainException("Account is not active");
        }
    }

    private void validateSameCurrency(Money amount) {
        if (!balance.getCurrency().equals(amount.getCurrency())) {
            throw new DomainException("Currency mismatch for account : " + iban);
        }
    }

    public void block() {
        this.status = AccountStatus.BLOCKED;
    }

    public void activate() {
        this.status = AccountStatus.ACTIVE;
    }

}

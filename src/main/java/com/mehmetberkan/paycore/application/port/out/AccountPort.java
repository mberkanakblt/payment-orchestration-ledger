package com.mehmetberkan.paycore.application.port.out;

import com.mehmetberkan.paycore.domain.aggregate.Account;

import java.util.Optional;

public interface AccountPort {
    Account save(Account account);
    Optional<Account> findById(Long id);
}

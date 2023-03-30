package com.bank.contas.domain.services;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountOperationService {

    BigDecimal consultBalance(UUID accountId);

    void deposit(UUID accountId, BigDecimal amount);

    void withdraw(UUID accountId, BigDecimal amount);

    void transfer(UUID accountIdOrigin, UUID accountIdDestiny, BigDecimal amount);
}

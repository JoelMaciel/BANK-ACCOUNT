package com.bank.contas.domain.services;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.api.models.AgencyDto;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AgencyService {

    Page<AgencyDto> findAll(Pageable pageable);
    Agency save(Agency agency);

    void delete(UUID agencyId);

    Agency searchOrFail(UUID agencyId);
}

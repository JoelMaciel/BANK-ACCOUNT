package com.bank.contas.domain.services;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UtilsService {

    String createUrlGetAllClientsByAccount(UUID accountId, Pageable pageable);
}

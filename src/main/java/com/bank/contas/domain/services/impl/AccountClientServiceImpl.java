package com.bank.contas.domain.services.impl;

import com.bank.contas.domain.repositories.AccountClientRepository;
import com.bank.contas.domain.services.AccountClientService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountClientServiceImpl implements AccountClientService {

    private final AccountClientRepository accountClientRepository;
}

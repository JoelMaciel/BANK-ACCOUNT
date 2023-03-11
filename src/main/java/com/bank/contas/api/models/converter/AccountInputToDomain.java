package com.bank.contas.api.models.converter;

import com.bank.contas.api.models.input.AccountInput;
import com.bank.contas.domain.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountInputToDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Account toDomainObject(AccountInput accountInput) {
        return modelMapper.map(accountInput, Account.class);
    }

    public void copyToDomainObject(AccountInput accountInput, Account account) {
        modelMapper.map(accountInput, account);
    }
}

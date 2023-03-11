package com.bank.contas.api.models.converter;

import com.bank.contas.api.models.input.AccountInput;
import com.bank.contas.api.models.input.AccountInputUpdate;
import com.bank.contas.domain.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountInputUpdateToDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Account toDomainObject(AccountInputUpdate accountUpdate) {
        return modelMapper.map(accountUpdate, Account.class);
    }

    public void copyToDomainObject(AccountInputUpdate accountUpdate, Account account) {
        modelMapper.map(accountUpdate, account);
    }
}

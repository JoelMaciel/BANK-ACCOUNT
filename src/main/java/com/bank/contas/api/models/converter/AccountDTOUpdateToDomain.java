package com.bank.contas.api.models.converter;

import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.domain.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOUpdateToDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Account toDomainObject(AccountDTOUpdate accountUpdate) {
        return modelMapper.map(accountUpdate, Account.class);
    }

    public void copyToDomainObject(AccountDTOUpdate accountUpdate, Account account) {
        modelMapper.map(accountUpdate, account);
    }
}

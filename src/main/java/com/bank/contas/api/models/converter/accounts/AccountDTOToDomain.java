package com.bank.contas.api.models.converter.accounts;

import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.request.AccountDTOUpdate;
import com.bank.contas.api.models.response.AccountSummaryDTO;
import com.bank.contas.domain.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountDTOToDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Account toDomainObject(AccountDTO accountDTO) {
        return modelMapper.map(accountDTO, Account.class);
    }

    public void copyToDomainObject(AccountDTO accountDTO, Account account) {
        modelMapper.map(accountDTO, account);
    }
    public void copyToDomainObjectSummary(AccountDTOUpdate accountDTOUpdate, Account account) {
        modelMapper.map(accountDTOUpdate, account);
    }
}

package com.bank.contas.api.models.converter;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.domain.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AccountToDto {

    @Autowired
    private ModelMapper modelMapper;

    public AccountDto converter(Account account) {
        return  modelMapper.map(account, AccountDto.class);
    }

    public Page<AccountDto> converterToPageDto(Page<Account> accountsPage, Pageable pageable) {
        var accountDtoPage = accountsPage.map(
                account -> modelMapper.map(account, AccountDto.class));
        return  accountDtoPage;
    }
}

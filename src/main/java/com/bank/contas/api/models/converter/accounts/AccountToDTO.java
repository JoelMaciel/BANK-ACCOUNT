package com.bank.contas.api.models.converter.accounts;

import com.bank.contas.api.models.request.AccountDTO;
import com.bank.contas.api.models.response.AccountSummaryDTO;
import com.bank.contas.domain.models.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AccountToDTO {

    @Autowired
    private ModelMapper modelMapper;

    public AccountDTO converter(Account account) {
        return  modelMapper.map(account, AccountDTO.class);
    }

    public AccountSummaryDTO converterSumarry(Account account) {
        return  modelMapper.map(account, AccountSummaryDTO.class);
    }

    public Page<AccountSummaryDTO> converterToPageDto(Page<Account> accountsPage, Pageable pageable) {
        var accountDtoPage = accountsPage.map(
                account -> modelMapper.map(account, AccountSummaryDTO.class));
        return  accountDtoPage;
    }
}

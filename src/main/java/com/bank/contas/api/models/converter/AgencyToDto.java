package com.bank.contas.api.models.converter;

import com.bank.contas.api.models.AccountDto;
import com.bank.contas.api.models.AgencyDto;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.Agency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AgencyToDto {

    @Autowired
    private ModelMapper modelMapper;

    public AgencyDto converter(Agency agency) {
        return  modelMapper.map(agency, AgencyDto.class);
    }

    public Page<AgencyDto> converterToPageDto(Page<Agency> agenciesPage, Pageable pageable) {
        var agenciesDtoPage = agenciesPage.map(
                agency -> modelMapper.map(agency, AgencyDto.class));
        return  agenciesDtoPage;
    }
}

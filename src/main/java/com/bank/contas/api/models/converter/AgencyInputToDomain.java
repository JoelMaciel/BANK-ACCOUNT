package com.bank.contas.api.models.converter;

import com.bank.contas.api.models.input.AccountInput;
import com.bank.contas.api.models.input.AgencyInput;
import com.bank.contas.domain.models.Account;
import com.bank.contas.domain.models.Agency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgencyInputToDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Agency toDomainObject(AgencyInput agencyInput) {
        return modelMapper.map(agencyInput, Agency.class);
    }

    public void copyToDomainObject(AgencyInput agencyInput, Agency agency) {
        modelMapper.map(agencyInput, agency);
    }
}

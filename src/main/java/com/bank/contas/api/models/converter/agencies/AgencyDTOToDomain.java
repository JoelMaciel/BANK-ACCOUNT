package com.bank.contas.api.models.converter.agencies;

import com.bank.contas.api.models.response.AgencyDTO;
import com.bank.contas.domain.models.Agency;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgencyDTOToDomain {

    @Autowired
    private ModelMapper modelMapper;

    public Agency toDomainObject(AgencyDTO agencyDTO) {

        return modelMapper.map(agencyDTO, Agency.class);
    }

    public void copyToDomainObject(AgencyDTO agencyDTO, Agency agency) {
        modelMapper.map(agencyDTO, agency);
    }
}

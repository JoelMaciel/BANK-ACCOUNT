package com.bank.contas.api.models.converter.agencies;

import com.bank.contas.api.models.response.AgencyDTO;
import com.bank.contas.domain.models.Agency;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AgencyToDTO {
    private final ModelMapper modelMapper;

    public AgencyDTO converter(Agency agency) {
        return  modelMapper.map(agency, AgencyDTO.class);
    }

    public Page<AgencyDTO> converterToPageDto(Page<Agency> agenciesPage, Pageable pageable) {
        var agenciesDtoPage = agenciesPage.map(
                agency -> modelMapper.map(agency, AgencyDTO.class));
        return  agenciesDtoPage;
    }
}

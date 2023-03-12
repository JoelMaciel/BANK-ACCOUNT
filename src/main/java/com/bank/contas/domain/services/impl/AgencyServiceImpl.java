package com.bank.contas.domain.services.impl;

import com.bank.contas.api.models.AgencyDto;
import com.bank.contas.api.models.converter.AgencyToDto;
import com.bank.contas.domain.exceptions.AgencyNameAndNumberExcepetion;
import com.bank.contas.domain.exceptions.AgencyNotFoundException;
import com.bank.contas.domain.exceptions.EntityInUseException;
import com.bank.contas.domain.models.Agency;
import com.bank.contas.domain.repositories.AgencyRepository;
import com.bank.contas.domain.services.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AgencyServiceImpl implements AgencyService {

    private static final String MSG_AGENCY_IN_USE =  "Code agency %s cannot be removed as it is in use";

    @Autowired
    private AgencyRepository agencyRepository;

    @Autowired
    private AgencyToDto agencyToDto;

    @Override
    public Page<AgencyDto> findAll(Pageable pageable) {
        Page<Agency> agencyPage = agencyRepository.findAll(pageable);
        return agencyToDto.converterToPageDto(agencyPage, pageable);
    }

    @Transactional
    @Override
    public Agency save(Agency agency) {

       if(existsAgencyAndNumber(agency.getName(), agency.getNumber())) {
           throw  new AgencyNameAndNumberExcepetion("Agency name or number already exists.");
       }
        return agencyRepository.save(agency);
    }

    @Transactional
    @Override
    public void delete(UUID agencyId) {
        try {
            agencyRepository.deleteById(agencyId);
            agencyRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new AgencyNotFoundException(agencyId);
        } catch (DataIntegrityViolationException e) {
            throw  new EntityInUseException(String.format(MSG_AGENCY_IN_USE, agencyId));
        }
    }

    @Override
    public Agency searchOrFail(UUID agencyId) {
        return agencyRepository.findById(agencyId)
                .orElseThrow(() -> new AgencyNotFoundException(agencyId));
    }

    @Override
    public boolean existsAgencyAndNumber(String name, String number) {
        return agencyRepository.existsByNameAndNumber(name, number);
    }

}

package com.bank.contas.domain.services.impl;

import com.bank.contas.api.models.converter.AgencyDTOToDomain;
import com.bank.contas.api.models.converter.AgencyToDTO;
import com.bank.contas.api.models.response.AgencyDTO;
import com.bank.contas.domain.exceptions.AgencyNotFoundException;
import com.bank.contas.domain.exceptions.EntityInUseException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.models.Agency;
import com.bank.contas.domain.repositories.AgencyRepository;
import com.bank.contas.domain.services.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AgencyServiceImpl implements AgencyService {
    private static final String MSG_AGENCY_IN_USE =  "Agency name already exists.";
    private static final String MSG_AGENCY_NOT_FOUND = "There is no agency with that name.";
    private final AgencyRepository agencyRepository;

    private final AgencyDTOToDomain agencyDTOToDomain;
    private final AgencyToDTO agencyToDto;

    @Override
    public Page<AgencyDTO> findAll(Specification<Agency> agency, Pageable pageable) {
        Page<Agency> agencyPage = agencyRepository.findAll(pageable);
        return agencyToDto.converterToPageDto(agencyPage, pageable);
    }

    @Override
    public AgencyDTO findByAgency(UUID agencyId) {
        try {
            return agencyToDto.converter(searchOrFail(agencyId));
        } catch (IllegalArgumentException e) {
            throw new EntityNotExistsException(MSG_AGENCY_NOT_FOUND);
        }
    }

    // verificar o porque nao esta tratando a execption DataIntegrityViolationException
    @Transactional
    @Override
    public AgencyDTO updateAgency(UUID agencyId, AgencyDTO agencyDTO) {
        try {
            var agency = searchOrFail(agencyId);
            agencyDTOToDomain.copyToDomainObject(agencyDTO, agency);
            return agencyToDto.converter(agencyRepository.save(agency));
        } catch (IllegalArgumentException e) {
            throw new EntityNotExistsException(MSG_AGENCY_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(MSG_AGENCY_IN_USE);
        }
    }

    @Override
    public Optional<Agency> findByNumber(String number) {
        return agencyRepository.findByNumber(number);
    }

    @Transactional
    @Override
    public AgencyDTO save(AgencyDTO agencyDTO) {

       if(existsByName(agencyDTO.getName())) {
           throw new EntityInUseException(MSG_AGENCY_IN_USE);
       }
       var agency = new Agency();
       agencyDTOToDomain.copyToDomainObject(agencyDTO, agency);
       return agencyToDto.converter(agencyRepository.save(agency));
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
    public boolean existsByName(String name) {
        return agencyRepository.existsByName(name);
    }

    @Override
    public boolean existsAgencyNumber(String number) {
        return agencyRepository.existsByNumber(number);
    }

    @Override
    public AgencyDTO findByName(String name) {
        return agencyRepository.findByName(name);
    }
}

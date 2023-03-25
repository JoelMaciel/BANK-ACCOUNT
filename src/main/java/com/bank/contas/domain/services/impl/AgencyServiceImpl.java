package com.bank.contas.domain.services.impl;

import com.bank.contas.api.models.request.AgencyDTO;
import com.bank.contas.api.models.response.AgencyResponseDTO;
import com.bank.contas.domain.exceptions.AgencyNotFoundException;
import com.bank.contas.domain.exceptions.DuplicateDataException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.models.AgencyModel;
import com.bank.contas.domain.repositories.AgencyRepository;
import com.bank.contas.domain.services.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AgencyServiceImpl implements AgencyService {
    private static final String DUPLICATE_DATA = "There is already an agency registered with that name or number.";
    private static final String MSG_AGENCY_NOT_FOUND = "There is no agency with that name.";
    private final AgencyRepository agencyRepository;


    public Page<AgencyResponseDTO> findAll(Specification<AgencyModel> agency, Pageable pageable) {
        Page<AgencyModel> agencyPages = agencyRepository.findAll(pageable);
        List<AgencyResponseDTO> agencyList = agencyPages.getContent().stream()
                .map(AgencyResponseDTO::toDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(agencyList, pageable, agencyPages.getTotalElements());
    }


    @Override
    public AgencyResponseDTO findByAgency(UUID agencyId) {
        try {
            return AgencyResponseDTO.toDTO(searchOrFail(agencyId));
        } catch (IllegalArgumentException e) {
            throw new EntityNotExistsException(MSG_AGENCY_NOT_FOUND);
        }
    }

    @Transactional
    @Override
    public AgencyResponseDTO updateAgency(UUID agencyId, AgencyDTO agencyDTO) {
        try {
            AgencyModel agencyModel = searchOrFail(agencyId).toBuilder()
                    .name(agencyDTO.getName())
                    .number(agencyDTO.getNumber())
                    .build();
            agencyRepository.save(agencyModel);
            agencyRepository.flush();
            return AgencyResponseDTO.toDTO(agencyModel);
        } catch (IllegalArgumentException e) {
            throw new EntityNotExistsException(MSG_AGENCY_NOT_FOUND);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException(DUPLICATE_DATA);
        }
    }

    @Transactional
    @Override
    public AgencyResponseDTO save(AgencyDTO agencyDTO) {
        try {
            existsByName(agencyDTO.getName());
            var newAgency = agencyRepository.save(AgencyModel.toEntity(agencyDTO));
            agencyRepository.flush();
            return AgencyResponseDTO.toDTO(newAgency);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateDataException(DUPLICATE_DATA);
        }
    }

    @Transactional
    @Override
    public void delete(UUID agencyId) {
        try {
            agencyRepository.deleteById(agencyId);
            agencyRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new AgencyNotFoundException(agencyId);
        }
    }

    @Override
    public AgencyModel searchOrFail(UUID agencyId) {
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
    public Optional<AgencyModel> findByNumber(String number) {
        return agencyRepository.findByNumber(number);
    }

}

package com.bank.contas.domain.services;

import com.bank.contas.api.models.request.AgencyDTO;
import com.bank.contas.api.models.response.AgencyResponseDTO;
import com.bank.contas.domain.models.AgencyModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface AgencyService {
    Page<AgencyResponseDTO> findAll(Specification<AgencyModel> agency, Pageable pageable);

    AgencyResponseDTO save(AgencyDTO agencyDTO);

    void delete(UUID agencyId);

    AgencyModel searchOrFail(UUID agencyId);

    boolean existsByName(String name);

    AgencyResponseDTO findByAgency(UUID agencyId);

    AgencyResponseDTO updateAgency(UUID agencyId, AgencyDTO agencyDTO);

   boolean existsAgencyNumber(String number);

    Optional<AgencyModel> findByNumber(String number);
}

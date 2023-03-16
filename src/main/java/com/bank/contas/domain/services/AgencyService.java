package com.bank.contas.domain.services;

import com.bank.contas.api.models.response.AgencyDTO;
import com.bank.contas.domain.models.Agency;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface AgencyService {

    Page<AgencyDTO> findAll(Specification<Agency> agency, Pageable pageable);

    Optional<Agency> findByNumber(String number);

    AgencyDTO save(AgencyDTO agencyDTO);

    void delete(UUID agencyId);

    Agency searchOrFail(UUID agencyId);

    boolean existsByName(String name);

    AgencyDTO findByAgency(UUID agencyId);

    boolean existsAgencyNumber(String number);

    AgencyDTO updateAgency(UUID agencyId, AgencyDTO agencyDTO);

}

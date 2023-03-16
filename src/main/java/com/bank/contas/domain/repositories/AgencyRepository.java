package com.bank.contas.domain.repositories;

import com.bank.contas.api.models.response.AgencyDTO;
import com.bank.contas.domain.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AgencyRepository extends JpaRepository<Agency, UUID>, JpaSpecificationExecutor<Agency> {

    boolean existsByName(String name);
    boolean existsByNumber(String number);
   Optional<Agency> findByNumber(String number);

    Optional<Agency> findByName(String name);
}

package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.AgencyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface AgencyRepository extends JpaRepository<AgencyModel, UUID>, JpaSpecificationExecutor<AgencyModel> {

    boolean existsByName(String name);
    boolean existsByNumber(String number);
   Optional<AgencyModel> findByNumber(String number);

}

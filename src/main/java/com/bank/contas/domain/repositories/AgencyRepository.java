package com.bank.contas.domain.repositories;

import com.bank.contas.domain.models.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AgencyRepository extends JpaRepository<Agency, UUID> {

    boolean existsByName(String name);

    boolean existsByNameAndNumber(String name, String number);
}

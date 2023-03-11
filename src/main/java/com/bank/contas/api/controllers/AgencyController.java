package com.bank.contas.api.controllers;

import com.bank.contas.api.models.AgencyDto;
import com.bank.contas.api.models.converter.AgencyInputToDomain;
import com.bank.contas.api.models.converter.AgencyToDto;
import com.bank.contas.api.models.input.AgencyInput;
import com.bank.contas.domain.models.Agency;
import com.bank.contas.domain.services.AgencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/agencies")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AgencyToDto agencyToDto;

    @Autowired
    private AgencyInputToDomain agencyInputToDomain;

    @GetMapping
    public Page<AgencyDto> getAllAgencies(@PageableDefault(page = 0, sort = "agencyId",
            direction = Sort.Direction.ASC) Pageable pageable) {

        var agenciesPage = agencyService.findAll(pageable);
        return agenciesPage;
    }

    @GetMapping("/{agencyId}")
    public AgencyDto getOneAgency(@PathVariable UUID agencyId) {
        var agencyCurrent= agencyService.searchOrFail(agencyId);

        var agencyDto = agencyToDto.converter(agencyCurrent);
        return  agencyDto;
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AgencyDto saveAgency(@RequestBody @Valid AgencyInput agencyInput) {
        var agency = agencyInputToDomain.toDomainObject(agencyInput);
        agency.setCreationDate(OffsetDateTime.now());
        agency.setUpdateDate(OffsetDateTime.now());
        agencyService.save(agency);

        var agencyDto = agencyToDto.converter(agency);
        return  agencyDto;
    }

    @PutMapping("/{agencyId}")
    public AgencyDto updateAgency(@PathVariable UUID agencyId, @RequestBody @Valid AgencyInput agencyInput) {
        Agency agencyCurrent = agencyService.searchOrFail(agencyId);

        agencyInputToDomain.copyToDomainObject(agencyInput, agencyCurrent);
        agencyCurrent.setUpdateDate(OffsetDateTime.now());
        agencyService.save(agencyCurrent);

        var agencyDto = agencyToDto.converter(agencyCurrent);
        return agencyDto;

    }

    @DeleteMapping("/{agencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgency(@PathVariable UUID agencyId) {
        agencyService.delete(agencyId);
    }
}

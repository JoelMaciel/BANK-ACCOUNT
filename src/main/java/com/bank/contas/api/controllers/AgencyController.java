package com.bank.contas.api.controllers;

import com.bank.contas.api.models.converter.agencies.AgencyToDTO;
import com.bank.contas.api.models.response.AgencyDTO;
import com.bank.contas.domain.services.AgencyService;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agencies")
public class AgencyController {

    private final AgencyService agencyService;
    private final AgencyToDTO agencyToDto;

    @GetMapping
    public Page<AgencyDTO> getAllAgencies(SpecificationTemplate.AgencySpec spec,
         @PageableDefault(page = 0, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return agencyService.findAll(spec, pageable);
    }

    @GetMapping("/{agencyId}")
    public AgencyDTO getOneAgency(@PathVariable UUID agencyId) {
        return agencyService.findByAgency(agencyId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AgencyDTO saveAgency(@RequestBody @Valid AgencyDTO agencyDTO) {
        return agencyService.save(agencyDTO);
    }

    @PutMapping("/{agencyId}")
    public AgencyDTO updateAgency(@PathVariable UUID agencyId, @RequestBody @Valid AgencyDTO agencyDTO) {
        return agencyService.updateAgency(agencyId, agencyDTO);
    }

    @DeleteMapping("/{agencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgency(@PathVariable UUID agencyId) {
        agencyService.delete(agencyId);
    }
}

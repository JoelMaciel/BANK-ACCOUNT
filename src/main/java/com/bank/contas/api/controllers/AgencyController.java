package com.bank.contas.api.controllers;

import com.bank.contas.api.dtos.response.AgencyResponseDTO;
import com.bank.contas.api.dtos.request.AgencyDTO;
import com.bank.contas.domain.services.AgencyService;
import com.bank.contas.infrastructure.specification.SpecificationTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/agencies")
public class AgencyController {

    private final AgencyService agencyService;

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping
    public Page<AgencyResponseDTO> getAllAgencies(SpecificationTemplate.AgencySpec spec,
         @PageableDefault(page = 0, sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return agencyService.findAll(spec, pageable);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    @GetMapping("/{agencyId}")
    public AgencyResponseDTO getOneAgency(@PathVariable UUID agencyId) {
        return agencyService.findByAgency(agencyId);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AgencyResponseDTO saveAgency(@RequestBody @Valid AgencyDTO agencyDTO) {
        return agencyService.save(agencyDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{agencyId}")
    public AgencyResponseDTO updateAgency(@PathVariable UUID agencyId, @RequestBody @Valid AgencyDTO agencyDTO) {
        return agencyService.updateAgency(agencyId, agencyDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{agencyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAgency(@PathVariable UUID agencyId) {
        agencyService.delete(agencyId);
    }
}

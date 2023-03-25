package com.bank.contas.api.models.response;

import com.bank.contas.domain.models.AgencyModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgencyResponseDTO {

    @JsonIgnore
    private UUID agencyId;
    private String name;
    private String number;

    public static AgencyResponseDTO toDTO(AgencyModel agencyModel) {
        return AgencyResponseDTO.builder()
                .agencyId(agencyModel.getAgencyId())
                .name(agencyModel.getName())
                .number(agencyModel.getNumber())
                .build();
    }
}

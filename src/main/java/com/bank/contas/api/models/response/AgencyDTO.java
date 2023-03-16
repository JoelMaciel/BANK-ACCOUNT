package com.bank.contas.api.models.response;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AgencyDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String number;
}

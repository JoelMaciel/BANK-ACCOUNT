package com.bank.contas.api.models.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Data
public class AgencyNumberDTO {

    @NotBlank
    private String number;

}

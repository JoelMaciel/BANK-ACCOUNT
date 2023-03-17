package com.bank.contas.api.models.response;

import lombok.Data;

@Data
public class ClientDTO {

    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;
    private AddressDto address;
}

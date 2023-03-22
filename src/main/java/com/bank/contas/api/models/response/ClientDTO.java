package com.bank.contas.api.models.response;

import com.bank.contas.domain.enums.ClientType;
import lombok.Data;

@Data
public class ClientDTO {

    private String name;
    private String cpf;
    private String email;
    private String phoneNumber;
    private ClientType clientType;
    private AddressDto address;
}

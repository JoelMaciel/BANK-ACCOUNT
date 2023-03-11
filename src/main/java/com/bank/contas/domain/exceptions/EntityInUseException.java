package com.bank.contas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntityInUseException extends BusinessException {

    private static final long serialVersionUID = 1L;
    public EntityInUseException(String mensagem) {
        super(mensagem);
    }

}

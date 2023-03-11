package com.bank.contas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotExistsException extends BusinessException{

    private static final long serialVersionUID = 1L;

    public EntityNotExistsException(String message) {
        super(message);
    }

}

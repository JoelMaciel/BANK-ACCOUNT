package com.bank.contas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotExistsException extends EntityNotExistsException{

    private static final long serialVersionUID = 1L;

    public  AccountNotExistsException(String message) {
        super(message);
    }

    public AccountNotExistsException(UUID accountId) {
        this(String.format("There is no account registration with code %s", accountId));
    }

}

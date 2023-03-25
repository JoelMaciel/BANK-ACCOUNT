package com.bank.contas.domain.exceptions;

import java.util.UUID;


public class AccountNotFoundException extends EntityNotExistsException{

    private static final long serialVersionUID = 1L;

    public AccountNotFoundException(String message) {
        super(message);
    }

}

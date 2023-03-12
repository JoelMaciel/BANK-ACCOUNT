package com.bank.contas.domain.exceptions;

import java.util.UUID;


public class AccountNotExistsException extends EntityNotExistsException{

    private static final long serialVersionUID = 1L;

    public  AccountNotExistsException(String message) {
        super(message);
    }

    public AccountNotExistsException(UUID accountId) {
        this(String.format("There is no account registration with code %s", accountId));
    }

}

package com.bank.contas.domain.exceptions;

public class RestrictedAccessException extends EntityNotExistsException{

    private static final long serialVersionUID = 1L;

    public RestrictedAccessException(String message) {
        super(message);
    }

}

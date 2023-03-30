package com.bank.contas.domain.exceptions;

public class AccountNotExistException extends EntityNotExistsException{

    private static final long serialVersionUID = 1L;

    public AccountNotExistException(String message) {
        super(message);
    }

}

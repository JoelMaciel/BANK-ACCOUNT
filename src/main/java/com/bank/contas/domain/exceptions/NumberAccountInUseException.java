package com.bank.contas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NumberAccountInUseException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public NumberAccountInUseException(String mensagem) {
        super(mensagem);
    }

    public NumberAccountInUseException(String mensagem,Throwable cause) {
        super(mensagem, cause);
    }
}

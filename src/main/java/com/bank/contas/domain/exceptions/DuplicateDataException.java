package com.bank.contas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateDataException extends BusinessException {

    private static final long serialVersionUID = 1L;
    public DuplicateDataException(String mensagem) {
        super(mensagem);
    }

}

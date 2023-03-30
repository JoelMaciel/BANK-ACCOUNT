package com.bank.contas.domain.exceptions;

public class AmountNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;
    public AmountNotFoundException(String mensagem) {
        super(mensagem);
    }

}

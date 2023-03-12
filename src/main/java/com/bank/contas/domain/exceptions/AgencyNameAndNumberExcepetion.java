package com.bank.contas.domain.exceptions;

public class AgencyNameAndNumberExcepetion extends EntityInUseException {
    public AgencyNameAndNumberExcepetion(String mensagem) {
        super(mensagem);
    }
}

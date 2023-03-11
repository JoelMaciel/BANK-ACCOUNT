package com.bank.contas.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AgencyNotFoundException extends EntityNotExistsException {
    private static final long serialVersionUID = 1L;

    public  AgencyNotFoundException(String message) {
        super(message);
    }

    public AgencyNotFoundException(UUID agencyId) {
        this(String.format("There is no agency registration with code %s", agencyId));
    }
}

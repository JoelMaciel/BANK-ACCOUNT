package com.bank.contas.domain.services.impl;

import com.bank.contas.domain.services.UtilsService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilsServiceImpl implements UtilsService {

    String REQUEST_URI = "http://localhost:8087";

    public String createUrl(UUID accountId, Pageable pageable) {
        return REQUEST_URI + "/clients?accountId=" + accountId + "&page=" + pageable.getPageNumber() + "&size="
                + pageable.getPageSize() + "&sort=" + pageable.getSort().toString().replaceAll(": ", ",");
    }
}

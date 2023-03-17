package com.bank.contas.api.clients;

import com.bank.contas.api.models.response.ClientDTO;
import com.bank.contas.api.models.response.ResponsePageDTO;
import com.bank.contas.domain.services.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Component
public class AccountRequestClient {

    private final RestTemplate restTemplate;

    private final UtilsService utilsService;

    String REQUEST_URI = "http://localhost:8087";

    public Page<ClientDTO> getAllClientsByAccount(UUID accountId, Pageable pageable) {
        List<ClientDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<ClientDTO>> result = null;

        String url = utilsService.createUrl(accountId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<ClientDTO>> responseType =
                                 new ParameterizedTypeReference<ResponsePageDTO<ClientDTO>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
        log.debug("Response Number of Elements: {}", searchResult.size());
        } catch (HttpStatusCodeException e) {
        log.error("Error request/ clients {}", e);
        }
        log.info("Ending request / client accountId {}", accountId);

        return result.getBody();
    }
}

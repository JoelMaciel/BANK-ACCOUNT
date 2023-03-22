package com.bank.contas.api.clients;

import com.bank.contas.api.models.request.AccountClientDTO;
import com.bank.contas.api.models.response.ClientDTO;
import com.bank.contas.api.models.response.ResponsePageDTO;
import com.bank.contas.domain.services.UtilsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
public class ClientRequestClient {

    private final RestTemplate restTemplate;

    private final UtilsService utilsService;

    @Value("${bank.api.url.user}")
    String REQUEST_URL_CLIENT;

    public Page<ClientDTO> getAllClientsByAccount(UUID accountId, Pageable pageable) {
        List<ClientDTO> searchResult = null;
        ResponseEntity<ResponsePageDTO<ClientDTO>> result = null;

        String url = REQUEST_URL_CLIENT + utilsService.createUrlGetAllClientsByAccount(accountId, pageable);

        log.debug("Request URL: {}", url);
        log.info("Request URL: {}", url);
        try {
            ParameterizedTypeReference<ResponsePageDTO<ClientDTO>> responseType =
                                 new ParameterizedTypeReference<ResponsePageDTO<ClientDTO>>() {};
            result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            searchResult = result.getBody().getContent();
        } catch (HttpStatusCodeException e) {
        log.error("Error request/ clients {}", e);
        }
        log.info("Ending request / client accountId {}", accountId);

        return result.getBody();
    }

    public ResponseEntity<ClientDTO> getOneClientById(UUID clientId) {
        String url = REQUEST_URL_CLIENT  + "/clients/" + clientId;
        log.info("Request URL: {}", url);
        return  restTemplate.exchange(url, HttpMethod.GET, null, ClientDTO.class);
    }

    public void postSubscriptionClientInAccount(UUID accountId, UUID clientId) {
        String url = REQUEST_URL_CLIENT + "/clients/" + clientId + "/accounts/subscription";
        log.info("Request URL: {}", url);
        var accountClientDTO = new AccountClientDTO();
        accountClientDTO.setClientId(clientId);
        accountClientDTO.setAccountId(accountId);
        restTemplate.postForObject(url, accountClientDTO, String.class);
    }

    public void deleteAccountInClient(UUID accountId) {
        String url = REQUEST_URL_CLIENT + "/clients/accounts/" + accountId;
        log.info("Request URL: {}", url);
        restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
    }
}

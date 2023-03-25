package com.bank.contas.api.controllers;

import com.bank.contas.api.models.response.AccountClientDTO;
import com.bank.contas.api.models.response.SubscriptionDTO;
import com.bank.contas.domain.models.UserModel;
import com.bank.contas.domain.services.AccountService;
import com.bank.contas.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountUserController {

    private final UserService userService;
    private final AccountService accountService;

//    @GetMapping("/accounts/{accountId}/users")
//    public UserModel getOneUserByAccount(@PathVariable(value = "accountId") UUID accountId,
//                                         @Pa) {
//      //  accountService.findAccountId(accountId);
//        return accountService.findUserByAccountId(accountId, userId);
//    }

    @PostMapping("/accounts/{accountId}/users/subscription")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountClientDTO saveSubscriptionClientInAccount(
            @PathVariable(value = "accountId") UUID accountId, @RequestBody @Valid SubscriptionDTO subscriptionDTO) {
        //  return userService.saveAndSubscriptionClientInAccount(accountId, subscriptionDTO);
        return null;
    }

    @DeleteMapping("/accounts/clients/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccountClientByClient(@PathVariable(value = "clientId") UUID clientId) {
        //userService.deleteAccountClientByClient(clientId);

    }

}

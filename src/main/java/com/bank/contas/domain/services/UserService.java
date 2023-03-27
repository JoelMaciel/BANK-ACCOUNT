package com.bank.contas.domain.services;

import com.bank.contas.domain.models.UserModel;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    UserModel save(UserModel userModel);

    void delete(UUID userId);

    boolean userIsActive(UUID userId);

    UserModel findById(UUID userId);
}

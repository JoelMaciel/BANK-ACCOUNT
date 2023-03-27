package com.bank.contas.domain.services.impl;

import com.bank.contas.domain.enums.UserStatus;
import com.bank.contas.domain.exceptions.BusinessException;
import com.bank.contas.domain.exceptions.EntityNotExistsException;
import com.bank.contas.domain.models.UserModel;
import com.bank.contas.domain.repositories.UserRepository;
import com.bank.contas.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final static String MSG_USER_NOT_FOUND = "There is no registered user with this id.";

    private final static String MSG_BLOCKED_USER = "It was not possible to register the user because he is blocked.";



    private final UserRepository userRepository;


    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public boolean userIsActive(UUID userId) {
       var user = findById(userId);
       if(user.getUserType().equals(UserStatus.BLOCKED)) {
           throw new BusinessException(MSG_BLOCKED_USER);
       }
        return false;
    }

    @Override
    public UserModel findById(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotExistsException(MSG_USER_NOT_FOUND));
    }

}

package com.bank.contas.domain.services.impl;

import com.bank.contas.domain.models.UserModel;
import com.bank.contas.domain.repositories.UserRepository;
import com.bank.contas.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    @Override
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    @Override
    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }
}

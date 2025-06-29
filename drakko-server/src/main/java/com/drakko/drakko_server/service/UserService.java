package com.drakko.drakko_server.service;

import com.drakko.drakko_server.dto.RegisterRequest;
import com.drakko.drakko_server.model.User;
import com.drakko.drakko_server.repository.UserRepository;
import com.drakko.drakko_server.utility.UserCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User register(RegisterRequest request) {
        String hash = UserCodeGenerator.hashPassword(request.getPassword());
        String code = UserCodeGenerator.generateCode(
                request.getFirstName(), request.getLastName(), request.getDob(), request.getPassword());

        User user = new User(null, request.getFirstName(), request.getLastName(), request.getDob(), hash, code, new ArrayList<>());
        return userRepository.save(user);
    }

    public Optional<User> findByUserCode(String code) {
        return userRepository.findByUserCode(code);
    }
}


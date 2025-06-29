package com.drakko.drakko_server.controller;

import com.drakko.drakko_server.dto.RegisterRequest;
import com.drakko.drakko_server.model.User;
import com.drakko.drakko_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok(user.getUserCode());
    }
}

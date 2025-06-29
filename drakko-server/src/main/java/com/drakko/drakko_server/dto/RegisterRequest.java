package com.drakko.drakko_server.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String password;
}
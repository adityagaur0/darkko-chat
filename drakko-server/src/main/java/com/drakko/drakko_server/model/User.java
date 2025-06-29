package com.drakko.drakko_server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String passwordHash;
    private String userCode; // AdityaG#8Lx6I

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    private List<Invite> receivedInvites;
}

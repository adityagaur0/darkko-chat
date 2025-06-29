package com.drakko.drakko_server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    @JsonManagedReference
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    @JsonManagedReference
    private User receiver;

    private InviteStatus status;

    @OneToOne
    private ChatRoom chatRoom;
}


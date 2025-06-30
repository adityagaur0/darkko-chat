package com.drakko.drakko_server.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String content;
    private Instant timeStamp;

    @ManyToOne
    private User sender;

    @ManyToOne
    private ChatRoom chatRoom;
}

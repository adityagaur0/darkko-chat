package com.drakko.drakko_server.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatWebSocketController {

    @MessageMapping("/chat/test")
    @SendTo("/topic/test")
    public String handleTestMessage(String message) {
        return "Echo from server: " + message;
    }
}

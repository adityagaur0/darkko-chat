package com.drakko.drakko_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://127.0.0.1:5500") // You can restrict this for prod
                .withSockJS(); // fallback for clients without native WS
        // Important: Remove .withSockJS() if you're testing with WebSocket King (it doesn’t support SockJS).
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // For subscriptions
        config.setApplicationDestinationPrefixes("/app"); // For sending
    }
}

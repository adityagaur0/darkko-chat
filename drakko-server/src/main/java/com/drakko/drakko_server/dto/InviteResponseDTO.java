package com.drakko.drakko_server.dto;

public record InviteResponseDTO(
        Long id,
        String senderCode,
        String receiverCode,
        String status
) {}


package com.drakko.drakko_server.dto;

import com.drakko.drakko_server.model.Invite;

public record InviteDTO (
     Long id,
     String senderName,
     String receiverName,
     String status
){}


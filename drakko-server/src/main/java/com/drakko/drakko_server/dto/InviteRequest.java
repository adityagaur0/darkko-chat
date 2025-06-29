package com.drakko.drakko_server.dto;

import lombok.Data;

@Data
public class InviteRequest {
    private String senderCode;
    private String receiverCode;
}

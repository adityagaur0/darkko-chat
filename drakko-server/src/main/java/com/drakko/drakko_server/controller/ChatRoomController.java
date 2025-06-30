package com.drakko.drakko_server.controller;

import com.drakko.drakko_server.model.ChatRoom;
import com.drakko.drakko_server.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping("/room")
    public ResponseEntity<ChatRoom> getOrCreateRoom(@RequestParam Long userAId, @RequestParam Long userBId) {
        ChatRoom room = chatRoomService.getOrCreateRoom(userAId, userBId);
        return ResponseEntity.ok(room);
    }
}


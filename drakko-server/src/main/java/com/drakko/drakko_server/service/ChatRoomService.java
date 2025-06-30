package com.drakko.drakko_server.service;

import com.drakko.drakko_server.model.ChatRoom;
import com.drakko.drakko_server.model.User;
import com.drakko.drakko_server.repository.ChatRoomRepository;
import com.drakko.drakko_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoom getOrCreateRoom(Long userAId, Long userBId) {
        if (userAId.equals(userBId)) throw new IllegalArgumentException("Cannot chat with self");

        Optional<ChatRoom> existingRoom = chatRoomRepository.findRoomByUserIds(userAId, userBId);
        if (existingRoom.isPresent()) return existingRoom.get();

        User userA = userRepository.findById(userAId)
                .orElseThrow(() -> new RuntimeException("User A not found"));
        User userB = userRepository.findById(userBId)
                .orElseThrow(() -> new RuntimeException("User B not found"));

        ChatRoom newRoom = new ChatRoom();
        newRoom.getParticipants().add(userA);
        newRoom.getParticipants().add(userB);
        return chatRoomRepository.save(newRoom);
    }
}

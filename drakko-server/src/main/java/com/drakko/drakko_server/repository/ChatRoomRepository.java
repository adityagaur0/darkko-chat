package com.drakko.drakko_server.repository;

import com.drakko.drakko_server.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {}

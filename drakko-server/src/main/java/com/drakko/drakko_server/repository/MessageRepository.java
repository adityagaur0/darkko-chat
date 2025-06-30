package com.drakko.drakko_server.repository;

import com.drakko.drakko_server.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoom_IdOrderByTimeStampAsc(Long chatRoomId);
}

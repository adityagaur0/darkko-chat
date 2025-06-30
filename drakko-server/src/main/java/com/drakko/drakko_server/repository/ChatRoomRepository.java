package com.drakko.drakko_server.repository;

import com.drakko.drakko_server.model.ChatRoom;
import com.drakko.drakko_server.model.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("""
        SELECT cr FROM ChatRoom cr
        JOIN cr.participants p1
        JOIN cr.participants p2
        WHERE p1.id = :userAId AND p2.id = :userBId
        AND SIZE(cr.participants) = 2
    """)
    Optional<ChatRoom> findRoomByUserIds(@Param("userAId") Long userAId, @Param("userBId") Long userBId);
}

package com.drakko.drakko_server.repository;

import com.drakko.drakko_server.model.Invite;
import com.drakko.drakko_server.model.InviteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InviteRepository extends JpaRepository<Invite, Long> {
    List<Invite> findByReceiver_UserCodeAndStatus(String userCode, InviteStatus status);
}

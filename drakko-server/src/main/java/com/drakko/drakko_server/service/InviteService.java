package com.drakko.drakko_server.service;

import com.drakko.drakko_server.dto.InviteDTO;
import com.drakko.drakko_server.dto.InviteResponseDTO;
import com.drakko.drakko_server.model.ChatRoom;
import com.drakko.drakko_server.model.Invite;
import com.drakko.drakko_server.model.InviteStatus;
import com.drakko.drakko_server.model.User;
import com.drakko.drakko_server.repository.ChatRoomRepository;
import com.drakko.drakko_server.repository.InviteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final UserService userService;
    private final InviteRepository inviteRepository;
    private final ChatRoomRepository chatRoomRepository;

    public InviteResponseDTO inviteResponseToDTO(Invite invite) {
        return new InviteResponseDTO(
                invite.getId(),
                invite.getSender().getUserCode(),
                invite.getReceiver().getUserCode(),
                invite.getStatus().name()
        );
    }
    public InviteDTO inviteToDTO(Invite invite) {
        return new InviteDTO(
        invite.getId(),
         invite.getSender().getFirstName() + " " + invite.getSender().getLastName(),
        invite.getReceiver().getFirstName() + " " + invite.getReceiver().getLastName(),
         invite.getStatus().name()
        );
    }

    public Invite sendInvite(String senderCode, String receiverCode) {
        User sender = userService.findByUserCode(senderCode).orElseThrow();
        User receiver = userService.findByUserCode(receiverCode).orElseThrow();

        Invite invite = new Invite();
        invite.setSender(sender);
        invite.setReceiver(receiver);
        invite.setStatus(InviteStatus.PENDING);
        return inviteRepository.save(invite);
    }

    public Invite acceptInvite(Long inviteId) {
        Invite invite = inviteRepository.findById(inviteId).orElseThrow();
        invite.setStatus(InviteStatus.ACCEPTED);

        ChatRoom room = new ChatRoom();
        room.setParticipants(List.of(invite.getSender(), invite.getReceiver()));
        room.setCreatedAt(LocalDateTime.now());

        chatRoomRepository.save(room);
        invite.setChatRoom(room);
        return inviteRepository.save(invite);
    }

    public List<Invite> getInvitesForUser(String userCode) {
        return inviteRepository.findByReceiver_UserCodeAndStatus(userCode, InviteStatus.PENDING);
    }
}


package com.drakko.drakko_server.service;

import com.drakko.drakko_server.dto.InviteDTO;
import com.drakko.drakko_server.dto.InviteResponseDTO;
import com.drakko.drakko_server.model.ChatRoom;
import com.drakko.drakko_server.model.Invite;
import com.drakko.drakko_server.model.InviteStatus;
import com.drakko.drakko_server.model.User;
import com.drakko.drakko_server.repository.ChatRoomRepository;
import com.drakko.drakko_server.repository.InviteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @Transactional
    public Invite acceptInvite(Long inviteId) {
        Invite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new IllegalArgumentException("Invite not found with id: " + inviteId));

        invite.setStatus(InviteStatus.ACCEPTED);

        User sender = invite.getSender();
        User receiver = invite.getReceiver();

        Long senderId = sender.getId();
        Long receiverId = receiver.getId();

        // Ensure consistent ordering to avoid mismatch (e.g., (1,2) vs (2,1))
        Long userAId = Math.min(senderId, receiverId);
        Long userBId = Math.max(senderId, receiverId);

        Optional<ChatRoom> existingRoom = chatRoomRepository.findRoomByUserIds(userAId, userBId);

        ChatRoom room = existingRoom.orElseGet(() -> {
            ChatRoom newRoom = new ChatRoom();
            newRoom.setParticipants(Set.of(sender, receiver)); // Use Set to avoid duplicates
            newRoom.setCreatedAt(LocalDateTime.now());
            return chatRoomRepository.save(newRoom);
        });

        invite.setChatRoom(room);
        return inviteRepository.save(invite);
    }



    public List<Invite> getInvitesForUser(String userCode) {
        return inviteRepository.findByReceiver_UserCodeAndStatus(userCode, InviteStatus.PENDING);
    }
}


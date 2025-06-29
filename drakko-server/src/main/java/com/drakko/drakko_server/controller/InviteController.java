package com.drakko.drakko_server.controller;

import com.drakko.drakko_server.dto.InviteDTO;
import com.drakko.drakko_server.dto.InviteRequest;
import com.drakko.drakko_server.dto.InviteResponseDTO;
import com.drakko.drakko_server.model.Invite;
import com.drakko.drakko_server.service.InviteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invite")
@RequiredArgsConstructor
public class InviteController {
    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<InviteResponseDTO> sendInvite(@RequestBody InviteRequest request) {
        Invite invite = inviteService.sendInvite(request.getSenderCode(), request.getReceiverCode());
        return ResponseEntity.ok(inviteService.inviteResponseToDTO(invite));
    }

    @PostMapping("/{inviteId}/accept")
    public ResponseEntity<Invite> accept(@PathVariable Long inviteId) {
        return ResponseEntity.ok(inviteService.acceptInvite(inviteId));
    }

    @GetMapping("{userCode}")
    public ResponseEntity<List<InviteDTO>> getPending(@PathVariable String userCode) {
        List<Invite> invites = inviteService.getInvitesForUser(userCode);
        List<InviteDTO> response = invites.stream().map(inviteService::inviteToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}

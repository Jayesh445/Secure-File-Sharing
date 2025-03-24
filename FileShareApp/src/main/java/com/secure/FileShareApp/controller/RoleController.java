package com.secure.FileShareApp.controller;

import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.RoleType;
import com.secure.FileShareApp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @PostMapping("/make-admin/{userId}")
    public ResponseEntity<UserResponseDto> makeAdmin(@PathVariable String userId) {
        UserResponseDto response = roleService.makeAdmin(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/revoke-admin/{userId}")
    public ResponseEntity<UserResponseDto> revokeAdmin(@PathVariable String userId) {
        UserResponseDto response = roleService.revokeAdmin(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/{roleType}")
    public ResponseEntity<List<UserResponseDto>> listUsersByRole(@PathVariable RoleType roleType) {
        List<UserResponseDto> users = roleService.listUsersByRole(roleType);
        return ResponseEntity.ok(users);
    }
}

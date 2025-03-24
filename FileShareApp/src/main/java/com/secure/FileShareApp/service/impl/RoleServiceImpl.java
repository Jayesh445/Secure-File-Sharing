package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.annotation.LogAction;
import com.secure.FileShareApp.annotation.UserIdParam;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.AuditAction;
import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.RoleType;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.repository.RoleRepository;
import com.secure.FileShareApp.repository.UserRepository;
import com.secure.FileShareApp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    @LogAction(action = AuditAction.MAKE_ADMIN)
    public UserResponseDto makeAdmin(@UserIdParam String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Role adminRole = roleRepository.findByRoleType(RoleType.ADMIN)
                .orElseGet(() -> {
                    Role newRole = new Role(RoleType.ADMIN);
                    return roleRepository.save(newRole);
                });
        user.setRole(adminRole);
        userRepository.save(user);
        return new UserResponseDto(user);
    }

    @Override
    @LogAction(action = AuditAction.REVOKE_ADMIN)
    public UserResponseDto revokeAdmin(@UserIdParam String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));

        Role userRole = roleRepository.findByRoleType(RoleType.USER)
                .orElseGet(() -> {
                    Role newRole = new Role(RoleType.USER);
                    return roleRepository.save(newRole);
                });

        user.setRole(userRole);
        userRepository.save(user);

        return new UserResponseDto(user);
    }

    @Override
    @LogAction(action = AuditAction.VIEW_USERS_BY_ROLE)
    public List<UserResponseDto> listUsersByRole(RoleType roleType) {
        List<User> users = userRepository.findByRole_RoleType(roleType);
        return users.stream().map(UserResponseDto::new).toList();
    }
}

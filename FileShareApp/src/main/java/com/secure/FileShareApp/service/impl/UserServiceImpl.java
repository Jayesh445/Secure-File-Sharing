package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.dto.AuthResponseDto;
import com.secure.FileShareApp.dto.LoginDto;
import com.secure.FileShareApp.dto.RoleAssignmentDto;
import com.secure.FileShareApp.dto.UserDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.RoleType;
import com.secure.FileShareApp.entity.User;
import com.secure.FileShareApp.exceptions.ResourceNotFoundException;
import com.secure.FileShareApp.exceptions.UserAlreadyExistsException;
import com.secure.FileShareApp.repository.RoleRepository;
import com.secure.FileShareApp.repository.UserRepository;
import com.secure.FileShareApp.service.JwtService;
import com.secure.FileShareApp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<AuthResponseDto> registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with "+userDto.getEmail()+" already exists");
        }
        Role defaultRole = roleRepository.findByRoleType(RoleType.USER.name())
                .orElseThrow(() -> new ResourceNotFoundException("Default role USER not found"));

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto);
        user.setPassword(encodedPassword);
        user.getRoles().add(defaultRole);
        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);
        String token = jwtService.generateToken(user);
        AuthResponseDto authResponseDto = new AuthResponseDto(token,userResponseDto);
        return ResponseEntity.ok(authResponseDto);
    }

    @Override
    public ResponseEntity<AuthResponseDto> authenticateUser(LoginDto loginDto) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteUser(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserByEmail(String email) {
        return null;
    }

    @Override
    public ResponseEntity<User> getUserById(String userId) {
        return null;
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDto> changePassword(UserDto userDto) {
        return null;
    }

    @Override
    public ResponseEntity<RoleAssignmentDto> assignRoleToUser(UserDto userDto, RoleType role) {
        return null;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

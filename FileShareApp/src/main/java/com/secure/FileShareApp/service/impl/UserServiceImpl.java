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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponseDto registerUser(UserDto userDto) {

        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with "+userDto.getEmail()+" already exists");
        }

        Role defaultRole = roleRepository.findByRoleType(RoleType.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Default role USER not found"));

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = new User(userDto);
        user.setPassword(encodedPassword);
        user.setRole(defaultRole);
        User savedUser = userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);
        System.out.println(userResponseDto.toString());
        String token = jwtService.generateToken(user);
        System.out.println("Token: " + token);
        return new AuthResponseDto(token,userResponseDto);
    }

    @Override
    public AuthResponseDto authenticateUser(LoginDto loginDto) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
        user.orElseThrow(() ->
                new ResourceNotFoundException("User with "+loginDto.getEmail()+" not found")
        );
        UserResponseDto userResponseDto = new UserResponseDto(user.get());
        String token = jwtService.generateToken(user.get());

        return new AuthResponseDto(token,userResponseDto);
    }

    @Override
    public UserResponseDto updateUser(UserDto userDto) {
        return null;
    }

    @Override
    public boolean deleteUser(UserDto userDto) {
        return false;
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        return null;
    }

    @Override
    public User getUserById(String userId) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return null;
    }

    @Override
    public UserResponseDto changePassword(UserDto userDto) {
        return null;
    }

    @Override
    public RoleAssignmentDto assignRoleToUser(UserDto userDto, RoleType role) {
        return null;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}

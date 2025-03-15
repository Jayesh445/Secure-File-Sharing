package com.secure.FileShareApp.service.impl;

import com.secure.FileShareApp.dto.AuthResponseDto;
import com.secure.FileShareApp.dto.LoginDto;
import com.secure.FileShareApp.dto.RoleAssignmentDto;
import com.secure.FileShareApp.dto.UserDto;
import com.secure.FileShareApp.dto.UserResponseDto;
import com.secure.FileShareApp.entity.AuditLogs;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
//        System.out.println(user);
        User savedUser = userRepository.save(user);
//        System.out.println(savedUser);
        UserResponseDto userResponseDto = new UserResponseDto(savedUser);
//        System.out.println("before jwt"+userResponseDto);
        String token = jwtService.generateToken(user);
//        System.out.println("after jwt"+userResponseDto);
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
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
//        System.out.println("userDto.getEmail():"+userDto.getEmail());
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with "+userDto.getEmail()+" not found");
        }
//        System.out.println(user.get().toString());
        user.get().setName(userDto.getName());
        User saved = userRepository.save(user.get());
        return new UserResponseDto(saved);
    }

    @Override
    @Transactional
    public void deleteUser(UserDto userDto) {
        if (!userRepository.existsById(userDto.getUserId())) {
            throw new ResourceNotFoundException("User with "+userDto.getUserId()+" not found");
        }
        userRepository.deleteUserByUserId(userDto.getUserId());
    }

    @Override
    public UserResponseDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with "+email+" not found");
        }
        return new UserResponseDto(user.get());
    }

    @Override
    public User getUserById(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User with "+userId+" not found");
        }
        return user.get();
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public UserResponseDto changePassword(UserDto userDto) {
        return null;
    }

    @Override  //TODO
    public RoleAssignmentDto assignRoleToUser(UserDto userDto, RoleType role) {
        return null;
    }

    @Override
    public void deactivateUser(String userId) {

    }

    @Override
    public void reactivateUser(String userId) {

    }

    @Override  //TODO
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with "+username+" not found");
        }
        return user.get();
    }
}

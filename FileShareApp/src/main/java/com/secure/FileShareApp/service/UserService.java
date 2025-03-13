package com.secure.FileShareApp.service;

import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public ResponseEntity<User> registerUser(User user);

    public ResponseEntity<User> authenticateUser(User user);

    public ResponseEntity<User> updateUser(User user);

    public ResponseEntity<User> deleteUser(User user);

    public ResponseEntity<User> getUserByEmail(String email);

    public ResponseEntity<User> getUserById(String userId);

    public ResponseEntity<List<User>> getAllUsers();

    public ResponseEntity<User> changePassword(User user);

    public ResponseEntity<User> assignRoleToUser(User user, Role role);
}


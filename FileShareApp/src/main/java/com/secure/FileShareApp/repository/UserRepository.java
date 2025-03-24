package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.RoleType;
import com.secure.FileShareApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);

    void deleteUserByUserId(String userId);

    List<User> findByRole_RoleType(RoleType roleRoleType);
}

package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByRoleType(RoleType roleType);
}


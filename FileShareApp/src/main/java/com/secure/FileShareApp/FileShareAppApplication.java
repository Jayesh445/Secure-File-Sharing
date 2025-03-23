package com.secure.FileShareApp;

import com.secure.FileShareApp.entity.FilePermission;
import com.secure.FileShareApp.entity.PermissionType;
import com.secure.FileShareApp.entity.Role;
import com.secure.FileShareApp.entity.RoleType;
import com.secure.FileShareApp.repository.FilePermissionRepository;
import com.secure.FileShareApp.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.Transactional;

import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableScheduling
public class FileShareAppApplication {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private FilePermissionRepository filePermissionRepository;

	public static void main(String[] args) {
		SpringApplication.run(FileShareAppApplication.class, args);
	}

	@Transactional
	@PostConstruct
	public void init() {
		Set<RoleType> roles = Set.of(RoleType.USER, RoleType.ADMIN, RoleType.VIEWER);
		for (RoleType roleType : roles) {
			roleRepository.findByRoleType(roleType).orElseGet(() -> {
				Role role = new Role(roleType);
				return roleRepository.save(role);
			});
		}
	}
}

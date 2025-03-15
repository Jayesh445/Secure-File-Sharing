package com.secure.FileShareApp.repository;

import com.secure.FileShareApp.entity.RequestAccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestAccessRepository extends JpaRepository<RequestAccess,String> {
}

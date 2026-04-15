package com.example.TP2_Guilda.repositorys;

import com.example.TP2_Guilda.model.audit.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}

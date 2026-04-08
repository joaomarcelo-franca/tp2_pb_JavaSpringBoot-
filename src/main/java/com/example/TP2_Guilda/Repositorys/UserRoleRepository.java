package com.example.TP2_Guilda.Repositorys;

import com.example.TP2_Guilda.model.audit.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}

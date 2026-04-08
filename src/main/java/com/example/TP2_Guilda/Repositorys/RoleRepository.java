package com.example.TP2_Guilda.Repositorys;

import com.example.TP2_Guilda.model.audit.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

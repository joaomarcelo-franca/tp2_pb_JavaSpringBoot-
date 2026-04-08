package com.example.TP2_Guilda.Repositorys;

import com.example.TP2_Guilda.model.audit.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}

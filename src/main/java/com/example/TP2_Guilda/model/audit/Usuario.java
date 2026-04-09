package com.example.TP2_Guilda.model.audit;

import com.example.TP2_Guilda.model.aventura.Aventureiro;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios",
        schema = "audit",
uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id", "email"}))
@Getter @Setter@AllArgsConstructor
@NoArgsConstructor @ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "usuario")
    private List<Aventureiro>  aventureiros;

    @OneToMany(mappedBy = "usuario")
    private List<AuditEntry> auditEntries;

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "email", length = 180, nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Column(name = "status", length = 30, nullable = false)
    private String status;

    @Column(name = "ultimo_login_em")
    private LocalDateTime ultimoLoginEm;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime criadoEm;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime atualizadoEm;


//    HELPRS

    public void addRole(Role role) {
        UserRole ur = new UserRole();

        UserRoleID id = new UserRoleID();
        id.setUsuarioId(this.id);
        id.setRoleId(role.getId());

        ur.setId(id);
        ur.setUsuario(this);
        ur.setRole(role);
        ur.setGrantedAt(LocalDateTime.now());

        this.userRoles.add(ur);
    }
}

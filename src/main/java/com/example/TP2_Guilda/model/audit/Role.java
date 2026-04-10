package com.example.TP2_Guilda.model.audit;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "roles",
        schema = "audit",
        uniqueConstraints = @UniqueConstraint(columnNames = {"organizacao_id", "nome"})
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(name = "nome", nullable = false, length = 60)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime criadoEm;

    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"),
            schema = "audit"
    )
    private List<Permission> permissions;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles;


}

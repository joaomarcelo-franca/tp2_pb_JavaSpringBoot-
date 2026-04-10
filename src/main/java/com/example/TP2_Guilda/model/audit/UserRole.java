package com.example.TP2_Guilda.model.audit;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"usuario_id", "role_id"}
        ),
        schema = "audit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserRole {


    @EmbeddedId
    private UserRoleID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "granted_at")
    private LocalDateTime grantedAt;
}

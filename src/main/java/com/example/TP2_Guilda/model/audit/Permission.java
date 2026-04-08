package com.example.TP2_Guilda.model.audit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(
        name = "permissions",
        schema = "audit"
)
@Getter @Setter@AllArgsConstructor
@NoArgsConstructor

public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 80)
    private String code;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}

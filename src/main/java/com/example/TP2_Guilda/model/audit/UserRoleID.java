package com.example.TP2_Guilda.model.audit;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Embeddable
@EqualsAndHashCode
public class UserRoleID {
    private Long usuarioId;
    private Long roleId;

}

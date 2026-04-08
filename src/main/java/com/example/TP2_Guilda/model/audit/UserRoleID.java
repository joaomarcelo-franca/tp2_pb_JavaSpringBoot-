package com.example.TP2_Guilda.model.audit;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
@Embeddable
public class UserRoleID {
    private Long usuarioId;
    private Long roleId;

}

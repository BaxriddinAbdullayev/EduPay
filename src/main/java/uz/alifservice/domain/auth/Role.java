package uz.alifservice.domain.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;

@Getter
@Setter
@Entity
@Table(name = "auth_roles")
public class Role extends Auditable<Long> {

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "is_active")
    private boolean active = true;
}

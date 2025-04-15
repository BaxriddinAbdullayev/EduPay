package uz.alifservice.domain.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.alifservice.domain.Auditable;
import uz.alifservice.enums.GeneralStatus;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "auth_users")
public class User extends Auditable<Long> {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "temp_username")
    private String tempUsername;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    @Column(name = "is_active")
    private Boolean active = Boolean.TRUE;

    // todo
//    @Column(name = "photo_id")
//    private String photoId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "photo_id", insertable = false, updatable = false)
//    private AttachEntity photo;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auth_users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    protected Set<Role> roles;
}

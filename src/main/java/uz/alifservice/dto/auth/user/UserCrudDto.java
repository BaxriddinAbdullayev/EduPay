package uz.alifservice.dto.auth.user;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericCrudDto;
import uz.alifservice.dto.auth.role.RoleDto;
import uz.alifservice.enums.GeneralStatus;

import java.util.Set;

@Getter
@Setter
public class UserCrudDto extends GenericCrudDto {

    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private String username;
    private String tempUsername;
    private String password;
    private GeneralStatus status;
    protected Set<RoleDto> roles;
}

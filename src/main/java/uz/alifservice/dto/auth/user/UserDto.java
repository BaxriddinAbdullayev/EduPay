package uz.alifservice.dto.auth.user;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;
import uz.alifservice.dto.auth.role.RoleDto;
import uz.alifservice.enums.GeneralStatus;

import java.util.Set;

@Getter
@Setter
public class UserDto extends GenericDto {

    private String fullName;
    private String username;
    private String tempUsername;
    private String password;
    private GeneralStatus status;
    protected Set<RoleDto> roles;
}

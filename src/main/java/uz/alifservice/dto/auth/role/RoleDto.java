package uz.alifservice.dto.auth.role;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;

@Getter
@Setter
public class RoleDto extends GenericDto {

    private String roleName;
    private String displayName;
}

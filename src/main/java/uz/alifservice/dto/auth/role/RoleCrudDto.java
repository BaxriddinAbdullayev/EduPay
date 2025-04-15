package uz.alifservice.dto.auth.role;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericCrudDto;

@Getter
@Setter
public class RoleCrudDto extends GenericCrudDto {

    private String roleName;
    private String displayName;
}

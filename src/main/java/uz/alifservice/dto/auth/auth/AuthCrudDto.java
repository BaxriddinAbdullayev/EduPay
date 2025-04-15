package uz.alifservice.dto.auth.auth;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericCrudDto;

@Getter
@Setter
public class AuthCrudDto extends GenericCrudDto {

    private String username;
    private String password;
    private String confirmCode;
}

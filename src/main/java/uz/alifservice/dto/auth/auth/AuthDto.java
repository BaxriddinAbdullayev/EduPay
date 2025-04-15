package uz.alifservice.dto.auth.auth;

import lombok.Getter;
import lombok.Setter;
import uz.alifservice.dto.GenericDto;

@Getter
@Setter
public class AuthDto extends GenericDto {

    private String username;
    private String password;
    private String confirmCode;
}

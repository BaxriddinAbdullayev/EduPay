package uz.alifservice.dto.auth.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.alifservice.dto.auth.user.UserDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthVerificationResDto {

    private String accessToken;
    private String refreshToken;
    private UserDto user;
}

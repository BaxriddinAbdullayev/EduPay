package uz.alifservice.mapper.auth;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.alifservice.domain.auth.User;
import uz.alifservice.dto.auth.user.UserCrudDto;
import uz.alifservice.dto.auth.user.UserDto;
import uz.alifservice.mapper.BaseCrudMapper;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        RoleMapper.class
})
public interface UserMapper extends BaseCrudMapper<User, UserDto, UserCrudDto> {

}

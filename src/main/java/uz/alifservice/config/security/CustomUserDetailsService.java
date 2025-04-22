package uz.alifservice.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.alifservice.domain.auth.Role;
import uz.alifservice.domain.auth.User;
import uz.alifservice.enums.GeneralStatus;
import uz.alifservice.repository.auth.RoleRepository;
import uz.alifservice.repository.auth.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdOrEmail) throws UsernameNotFoundException {
        Optional<User> optional;
        try {
            // Try to find by ID (for JWT)
            optional = userRepository.findByIdAndDeletedFalse(Long.parseLong(userIdOrEmail));
        } catch (NumberFormatException e) {
            // If not an ID, try by email (for OAuth2)
            optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(userIdOrEmail);
        }
        if (optional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = optional.get();
        Set<Role> roleList = roleRepository.findRoleListByUserId(user.getId());
        return new CustomUserDetails(user, roleList);
    }

    public UserDetails createOrUpdateOAuth2User(String email, String name) {
        Optional<User> optional = userRepository.findByUsernameAndActiveTrueAndDeletedFalse(email);
        User user;
        if (optional.isPresent()) {
            user = optional.get();
        } else {
            Role roleUser = roleRepository.findByRoleNameAndActiveTrueAndDeletedFalse("ROLE_USER");
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(roleUser);

            user = new User();
            user.setUsername(email);
            user.setFullName(name);
            user.setPassword(""); // No password for OAuth2 users
            user.setRoles(roleSet);
            user.setStatus(GeneralStatus.ACTIVE);
            user = userRepository.save(user);
        }

        Set<Role> roleList = roleRepository.findRoleListByUserId(user.getId());
        return new CustomUserDetails(user, roleList);
    }
}

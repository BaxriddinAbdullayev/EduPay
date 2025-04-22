package uz.alifservice.repository.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.alifservice.domain.auth.Role;

import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    @Query(value = """
            select ar.*
            from auth_roles ar
                     inner join auth_users_roles aur on ar.id = aur.role_id
            where aur.user_id = :userId
              and ar.is_active = true
              and ar.is_deleted = false
            """, nativeQuery = true)
    Set<Role> findRoleListByUserId(@Param("userId") Long userId);

    Role findByRoleNameAndActiveTrueAndDeletedFalse(String name);
}
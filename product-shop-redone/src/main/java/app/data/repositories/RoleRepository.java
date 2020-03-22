package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import app.data.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByAuthority(String string);

	
}

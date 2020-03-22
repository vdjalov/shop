package app.data.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import app.data.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	UserDetails findByUsername(String username);

	Optional<User> findByUsernameAndPassword(String username, String password);
	
	@Query(value = "select * from users", nativeQuery = true)
	Page<User> findAllUsers(Pageable pageable);
	
}

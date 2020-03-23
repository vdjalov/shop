package app.service;

import java.util.List;
import java.util.Optional;

import app.data.models.Role;

public interface RoleService {

	void seedDb();
	long getRepositoryCount();
	Optional<Role> getByAuthority(String authority);
	List<Role> findAll();
}

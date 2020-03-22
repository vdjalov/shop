package app.service;

import java.util.List;

import app.data.models.Role;

public interface RoleService {

	void seedDb();
	long getRepositoryCount();
	Role getByAuthority(String authority);
	List<Role> findAll();
}

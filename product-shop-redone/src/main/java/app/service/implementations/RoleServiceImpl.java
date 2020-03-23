package app.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.models.Role;
import app.data.repositories.RoleRepository;
import app.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}


	@Override
	public void seedDb() {
		
		roleRepository.saveAll(Set.of(
					new Role("ROOT"), 
					new Role("ADMIN"), 
					new Role("MODERATOR"), 
					new Role("USER") 
				));
	}


	@Override
	public long getRepositoryCount() {
		return this.roleRepository.count();
	}


	@Override
	public Optional<Role> getByAuthority(String authority) {
		return this.roleRepository.findByAuthority(authority);
	}


	@Override
	public List<Role> findAll() {
		return this.roleRepository.findAll();
	}

}

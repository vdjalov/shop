package app.service.implementations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.data.models.Role;
import app.data.models.User;
import app.data.repositories.UserRepository;
import app.service.RoleService;
import app.service.UserService;
import app.service.models.EditUserProfileModel;
import app.service.models.ValidateUserRegisterModel;
import app.web.models.UserProfileViewModel;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private RoleService roleService;
	private ModelMapper modelMapper;
	private BCryptPasswordEncoder bCrypt;
	
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCrypt) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.modelMapper = modelMapper;
		this.bCrypt = bCrypt;
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username);
	}


	@Override
	public ValidateUserRegisterModel registerUser(@Valid ValidateUserRegisterModel validateUserRegisterModel) {
		if(this.roleService.getRepositoryCount() == 0) {
			this.roleService.seedDb();
		}
		User user = this.modelMapper.map(validateUserRegisterModel, User.class);
		String password = validateUserRegisterModel.getPassword();
		user.setPassword(this.bCrypt.encode(password));
		if(this.userRepository.count() == 0) {
			Set<Role> allRoles = this.roleService.findAll().stream().collect(Collectors.toSet());
			user.setAuthorities(allRoles);
		} else {
			user.setAuthorities(new HashSet<>(Set.of(this.roleService.getByAuthority("USER"))));
		}
		
		User returnedUser = this.userRepository.save(user);
		return this.modelMapper.map(returnedUser, ValidateUserRegisterModel.class);
	}


	@Override
	public UserProfileViewModel findByUsername(String name) {
		UserDetails user = this.userRepository.findByUsername(name);
		return this.modelMapper.map(user, UserProfileViewModel.class);
	}


	@Override
	public int updateUserDetails(EditUserProfileModel editUserProfileModel) {
		
		
		if(!editUserProfileModel.getNewPassword().equals(editUserProfileModel.getConfirmNewPassword())) {
			return 0;
		}

		String rawPassword = editUserProfileModel.getOldPassword();
		String encodedPassword = this.userRepository.findByUsername(editUserProfileModel.getUsername()).getPassword();
		if(!bCrypt.matches(rawPassword, encodedPassword)) {
			return -1;
		};
		
		if(!editUserProfileModel.getEmail().matches("(^[\\W\\w]+)@{1}([\\W\\w]+)\\.([a-z]{2,3}$)")) {
			return 1;
		}
		
		User user = (User) this.loadUserByUsername(editUserProfileModel.getUsername());
		user.setEmail(editUserProfileModel.getEmail());
		if(!editUserProfileModel.getNewPassword().trim().isEmpty()) {
			user.setPassword(this.bCrypt.encode(editUserProfileModel.getNewPassword()));
		}
		this.userRepository.save(user);
		return -5;
	}



	
	
}

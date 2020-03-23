package app.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import app.data.models.User;
import app.service.models.EditUserProfileModel;
import app.service.models.ValidateUserRegisterModel;
import app.web.models.UserProfileViewModel;

public interface UserService extends UserDetailsService{

	ValidateUserRegisterModel registerUser(@Valid ValidateUserRegisterModel validateUserRegisterModel);

	UserProfileViewModel findByUsername(String name);

	int updateUserDetails(@Valid EditUserProfileModel editUserProfileModel);

	Page<User> findAllUsers(Optional<Integer> page, Optional<String> sortBy);

	void updateUserAuthority(long userId, String role) throws Exception;

	
}

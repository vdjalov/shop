package app.service;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UserDetailsService;

import app.service.models.EditUserProfileModel;
import app.service.models.ValidateUserRegisterModel;
import app.web.models.UserProfileViewModel;

public interface UserService extends UserDetailsService{

	ValidateUserRegisterModel registerUser(@Valid ValidateUserRegisterModel validateUserRegisterModel);

	UserProfileViewModel findByUsername(String name);

	int updateUserDetails(@Valid EditUserProfileModel editUserProfileModel);

	
}

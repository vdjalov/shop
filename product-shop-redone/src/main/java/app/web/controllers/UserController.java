package app.web.controllers;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.service.UserService;
import app.service.models.EditUserProfileModel;
import app.web.models.UserProfileViewModel;

@Controller
@RequestMapping("/users")
public class UserController {

	public static final String USER_PROFILE_VIEW = "userTemplates/profile";
	public static final String EDIT_USER_PROFILE_VIEW = "userTemplates/edit-profile";
	public static final String ALL_USERS_VIEW = "userTemplates/all-users";
	
	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}


	@GetMapping("/profile")
	public ModelAndView getUserProfileDetails(ModelAndView modelAndView, Principal principal) {
		
		UserProfileViewModel userProfileViewModel = this.userService.findByUsername(principal.getName());
		modelAndView.setViewName(USER_PROFILE_VIEW);
		modelAndView.addObject("user", userProfileViewModel);
		return modelAndView;
	}
	
	
	@GetMapping("/edit")
	public ModelAndView getEditUserProfileDetailsView(ModelAndView modelAndView, Principal principal) {
		
		UserProfileViewModel userProfileViewModel = this.userService.findByUsername(principal.getName());
		modelAndView.setViewName(EDIT_USER_PROFILE_VIEW);
		modelAndView.addObject("user", userProfileViewModel);
		return modelAndView;
	}
	
	
	@PostMapping("/edit")
	public ModelAndView editUserProfileDetails(@ModelAttribute EditUserProfileModel editUserProfileModel, ModelAndView modelAndView) {
		int result = this.userService.updateUserDetails(editUserProfileModel);
		modelAndView.setViewName(EDIT_USER_PROFILE_VIEW);
		modelAndView.addObject("user", editUserProfileModel);
		if(result == -1) {
			modelAndView.addObject("oldPassword", "incorrect password");
			return modelAndView;
		} else if(result == 0) {
			modelAndView.addObject("newPassword", "new password must match confirm new password");
			return modelAndView;
		}  else if(result == 1) {
			modelAndView.addObject("email", "incorrect email");
			return modelAndView;
		}
		
		return new ModelAndView("redirect:/users/profile");
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('ROOT', 'ADMIN')")
	public ModelAndView getAllUsers(ModelAndView modelAndView) {
		modelAndView.setViewName(ALL_USERS_VIEW);
		return modelAndView;
	}
	
	
}


















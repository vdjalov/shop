package app.web.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.service.UserService;
import app.service.models.ValidateUserRegisterModel;

@Controller
@RequestMapping("/users")
public class AuthController {
	
	public static final String REGISTER_VIEW = "authTemplates/register";
	public static final String LOGIN_VIEW = "authTemplates/login";
	
	
	
	private UserService userService;
	
	
	@Autowired
	public AuthController( UserService userService) {
		this.userService = userService;
	}

	
	@ModelAttribute("validateUser")
	public ValidateUserRegisterModel ValidateUserRegisterModel() {
		return new ValidateUserRegisterModel();
	}
	

	@GetMapping("/register")
	public ModelAndView getRegisterView(@ModelAttribute("validateUser") ValidateUserRegisterModel validateUserRegisterModel, ModelAndView modelAndView) {
		modelAndView.setViewName(REGISTER_VIEW);
		return modelAndView;
	}
	
	
	@PostMapping("/register")
	public ModelAndView validateRegistrationView(@Valid@ModelAttribute("validateUser") ValidateUserRegisterModel validateUserRegisterModel
			, BindingResult bindingResult) {
			
			if(bindingResult.hasErrors()) {
				return new ModelAndView(REGISTER_VIEW);
			}
			
			ValidateUserRegisterModel userValidated = userService.registerUser(validateUserRegisterModel); // ? 
			return new ModelAndView("redirect:/users/login");
	}
	
	
	@GetMapping("/login")
	public ModelAndView getLoginView(ModelAndView modelAndView) {
		modelAndView.setViewName(LOGIN_VIEW);
		return modelAndView;
	}
	
	@GetMapping("/login/failure")
	public ModelAndView getLoginFailureView(ModelAndView modelAndView) {
		modelAndView.addObject("fail", "username or password incorrect or empty");
		modelAndView.setViewName(LOGIN_VIEW);
		return modelAndView;
	}
	
	
}







package app.web.api;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import app.data.models.User;
import app.service.UserService;

@RestController
@RequestMapping("/api")
public class UsersApi {

	private UserService userService;
	
	@Autowired
	public UsersApi(UserService userService) {
		this.userService = userService;
	}


	@GetMapping(value = "/allUsers", produces = "application/json")
	public Page<User> getAllUsers(@RequestParam Optional<Integer> page, 
			 					  @RequestParam Optional<String> sortBy) {
		Page<User> users =  this.userService.findAllUsers(page, sortBy);
		return users;
	}
	
	
	@PostMapping(value = "/users/set-role/{role}/{userId}")
	public ModelAndView setRole(@PathVariable("role") String role, 
			 					@PathVariable("userId") Long userId,
			 					HttpServletRequest request) {
	
		try {
			this.userService.updateUserAuthority(userId, role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String referer = request.getHeader("referer").substring(22); // 
		return new ModelAndView("redirect:/" + referer);
	}
	
	
}

















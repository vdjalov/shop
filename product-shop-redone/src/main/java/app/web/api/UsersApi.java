package app.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.models.User;
import app.data.repositories.UserRepository;

@RestController
@RequestMapping("/api")
public class UsersApi {

	private UserRepository userRepository;
	
	@Autowired
	public UsersApi(UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@GetMapping(value = "/allUsers", produces = "application/json")
	public Page<User> getAllUsers(@RequestParam Optional<Integer> page, 
			 					  @RequestParam Optional<String> sortBy) {
		Page<User> users =  this.userRepository.findAllUsers(PageRequest.of(page.orElse(0), 5,
																			Sort.by(Direction.ASC, sortBy.orElse("id"))));
									
		users.getContent().forEach(user -> user.setPassword(null));
		return users;
	}
	
}



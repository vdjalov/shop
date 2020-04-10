package app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Unauthorised {

	public static final String UNAUTHORISED_VIEW = "error/unauthorised";
	
	
	@GetMapping("/unauthorised")
	public ModelAndView getUnuthorisedUserView(ModelAndView modelAndView) {
		modelAndView.setViewName(UNAUTHORISED_VIEW);
		return modelAndView;
	}
}

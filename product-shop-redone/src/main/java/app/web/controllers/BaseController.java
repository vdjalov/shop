package app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BaseController {

	public static final String INDEX_VIEW = "index";
	public static final String HOME_VIEW = "home";
	
	@GetMapping("/index")
	public ModelAndView getIndexView(ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		return modelAndView;
	}

	@GetMapping("/home")
	public ModelAndView getHomeView(ModelAndView modelAndView) {
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
}

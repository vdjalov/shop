package app.web.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.service.CategoryService;
import app.service.ProductService;
import app.web.models.CategoryViewModel;

@Controller
public class BaseController {

	public static final String INDEX_VIEW = "index";
	public static final String HOME_VIEW = "home";
	
	
	private CategoryService categoryService;
	private ProductService productService;
	
	
	@Autowired
	public BaseController(CategoryService categoryService, ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@GetMapping("/index")
	public ModelAndView getIndexView(ModelAndView modelAndView) {
		modelAndView.setViewName(INDEX_VIEW);
		return modelAndView;
	}

	@GetMapping("/home")
	public ModelAndView getHomeView(ModelAndView modelAndView) {
		modelAndView.setViewName(HOME_VIEW);
		return modelAndView;
	}
	
}


























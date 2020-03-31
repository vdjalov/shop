package app.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.service.CategoryService;
import app.web.models.CategoryViewModel;

@RestController
@RequestMapping("/api")
public class CategoryApi {

	private CategoryService categoryService;

	
	@Autowired
	public CategoryApi(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	
	@GetMapping(value = "/category/all", produces = "application/json")
	public List<CategoryViewModel>getAllCategories() {
		return this.categoryService.getAllCategories();
	}
	
	
}

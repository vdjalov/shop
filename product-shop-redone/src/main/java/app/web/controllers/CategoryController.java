package app.web.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.service.CategoryService;
import app.service.models.AddCategoryModel;
import app.web.models.CategoryViewModel;

@Controller
@RequestMapping("/categories")
public class CategoryController {
	
	public static final String ADD_CATEGORY_VIEW = "categoryTemplates/add-category";
	public static final String ALL_CATEGORIES_VIEW = "categoryTemplates/all-categories";
	public static final String EDIT_CATEGORY_VIEW = "categoryTemplates/edit-category";
	public static final String DELETE_CATEGORY_VIEW = "categoryTemplates/delete-category";
	
	private CategoryService categoryService;
	
	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}


	@ModelAttribute("addCategory")
	public AddCategoryModel addCategoryModel() {
		return new AddCategoryModel();
	}
	
	
	@GetMapping("/add")
	public ModelAndView getCategoryView(@ModelAttribute("addCategory") AddCategoryModel addCategoryModel, ModelAndView modelAndView) {
		modelAndView .setViewName(ADD_CATEGORY_VIEW);
		return modelAndView;
	}
	
	
	@PostMapping("/add")
	public ModelAndView addCategory(@Valid@ModelAttribute("addCategory") AddCategoryModel addCategoryModel, BindingResult bindingResult,
			ModelAndView modelAndView) {
		modelAndView .setViewName(ADD_CATEGORY_VIEW);
		
		if(bindingResult.hasErrors()) {
			return modelAndView;
		}
		
		this.categoryService.addCategory(addCategoryModel);
		return new ModelAndView("redirect:/categories/all");
	}
	
	@GetMapping("/all")
	public ModelAndView getAllCategoriesView(ModelAndView modelAndView, HttpSession session) {
		modelAndView .setViewName(ALL_CATEGORIES_VIEW);
		List<CategoryViewModel> allCategories = this.categoryService.getAllCategories();
		modelAndView.addObject("categories", allCategories);
		return modelAndView;
	}
	
	
	@GetMapping("/edit/{categoryId}")
	public ModelAndView getEditCategoryView(@PathVariable("categoryId") long categoryId, ModelAndView modelAndView) {
		modelAndView .setViewName(EDIT_CATEGORY_VIEW);
		CategoryViewModel category = null;
		try {
			category = this.categoryService.getByCategoryId(categoryId);
		} catch (Exception e) {
			System.out.println(e.getMessage()); // Throw not found...
		}
		modelAndView.addObject("cat", category);
		return modelAndView;
	}
	
	
	@PostMapping("/edit/{categoryId}")
	public ModelAndView editCategoryConfirmChanges (@PathVariable("categoryId") long categoryId, @ModelAttribute("name") String categoryName,
				ModelAndView modelAndView) {
		modelAndView .setViewName(EDIT_CATEGORY_VIEW);
		CategoryViewModel category = new CategoryViewModel();
		try {
			 category = this.categoryService.confirmCategoryChanges(categoryId, categoryName);
			 modelAndView.addObject("edited", "category edited");
		} catch (Exception e) {
			modelAndView.addObject("error", e.getMessage());
			category.setId(categoryId);
			category.setCategory(categoryName);
		}
		modelAndView.addObject("cat", category);
		return modelAndView;
	}
	
	
	@GetMapping("/delete/{categoryId}")
	public ModelAndView getDeleteCategoryView(@PathVariable("categoryId") long categoryId, ModelAndView modelAndView) {
		modelAndView .setViewName(DELETE_CATEGORY_VIEW);
		CategoryViewModel categoryViewModel = null;
		try {
			categoryViewModel = this.categoryService.getByCategoryId(categoryId);
		} catch (Exception e) {
			System.out.println(e.getMessage()); // Intercept exception
			e.printStackTrace();
		}
		modelAndView.addObject("category", categoryViewModel);
		return modelAndView;
	}
	
	
	@PostMapping("/delete/{categoryId}")
	public ModelAndView confirmDeleteCategoryView(@PathVariable("categoryId") long categoryId, ModelAndView modelAndView, RedirectAttributes redir) {
		modelAndView .setViewName("redirect:/categories/all");
		CategoryViewModel categoryViewModel = null;
		try {
			categoryViewModel = this.categoryService.deleteCategoryById(categoryId);
			redir.addFlashAttribute("success", "category " + categoryViewModel.getCategory() + " deleted");
		} catch (Exception e) {
			System.out.println(e.getMessage());		// Intercept exception
			e.printStackTrace();
		}
		return modelAndView;
	}
}























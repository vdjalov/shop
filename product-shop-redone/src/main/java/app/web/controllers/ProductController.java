package app.web.controllers;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.service.CategoryService;
import app.service.ProductService;
import app.service.models.AddProductModel;
import app.web.models.CategoryViewModel;

@Controller
@RequestMapping("/products")
public class ProductController {

	public static final String ADD_PRODUCT_VIEW = "productTemplates/add-product";
	public static final String ALL_PRODUCTS_VIEW = "productTemplates/all-products";

	
	private CategoryService categoryService;
	private ProductService productService;
	
	@Autowired
	public ProductController(CategoryService categoryService, ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}
	
	
	@ModelAttribute("validateProduct")
	public AddProductModel addProductModel() {
		return new AddProductModel();
	}

	
	@GetMapping("/add")
	public ModelAndView getAddProductView(@ModelAttribute("validateProduct") AddProductModel addProductModel, ModelAndView modelAndView) {
		List<CategoryViewModel> categories = this.categoryService.getAllCategories();
		modelAndView.addObject("categories", categories);
		modelAndView.setViewName(ADD_PRODUCT_VIEW);
		return modelAndView;
	}
	
	
	@PostMapping("/add")
	public ModelAndView confirmAddProduct(@Valid@ModelAttribute("validateProduct") AddProductModel addProductModel,
			BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView(ADD_PRODUCT_VIEW);
		}
		try {
			this.productService.addProduct(addProductModel);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return new ModelAndView("redirect:/products/all");
	}
	
	
	@GetMapping("/all")
	public ModelAndView getAllProductsView(ModelAndView modelAndView) {
		modelAndView.setViewName(ALL_PRODUCTS_VIEW);
		return modelAndView;
	}
	
}

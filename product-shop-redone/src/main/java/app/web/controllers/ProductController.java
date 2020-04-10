package app.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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

import app.service.CategoryService;
import app.service.ProductService;
import app.service.models.AddProductModel;
import app.service.models.EditProductValidateModel;
import app.web.models.CategoryViewModel;
import app.web.models.ProductViewModel;

@Controller
@RequestMapping("/products")
public class ProductController {

	public static final String ADD_PRODUCT_VIEW = "productTemplates/add-product";
	public static final String ALL_PRODUCTS_VIEW = "productTemplates/all-products";
	public static final String DETAILS_PRODUCT_VIEW = "productTemplates/details-product";
	public static final String EDIT_PRODUCT_VIEW = "productTemplates/edit-product";
	public static final String DELETE_PRODUCT_VIEW = "productTemplates/delete-product";
	
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
	
	@ModelAttribute("editProduct")
	public EditProductValidateModel EditProductValidateModel() {
		return new EditProductValidateModel();
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
	
	
	@GetMapping("/details/{id}")
	public ModelAndView getProductDetailsView(@PathVariable("id") Long id, ModelAndView modelAndView) throws Exception {
		ProductViewModel productViewModel = this.productService.findProductById(id);
		modelAndView.addObject("product", productViewModel);
		modelAndView.setViewName(DETAILS_PRODUCT_VIEW);
		return modelAndView;
	}
	
	
	@GetMapping("/edit/{id}")
	public ModelAndView editProductView(@ModelAttribute("editProduct") EditProductValidateModel editProductValidateModel, @PathVariable("id") Long id, ModelAndView modelAndView) throws Exception {
		ProductViewModel productViewModel = this.productService.findProductById(id);
		List<String> allCategories = this.productService.getAllCategories();
		List<String> productCategories = productViewModel.getCategories().stream().map(cat -> cat.getCategory()).collect(Collectors.toList());
		
		modelAndView.addObject("allCategories", allCategories);
		modelAndView.addObject("productCategories", productCategories);
		modelAndView.addObject("product", productViewModel);
		modelAndView.setViewName(EDIT_PRODUCT_VIEW);
		return modelAndView;
	}
	
	@PostMapping("/edit/{id}")
	public ModelAndView confirmEditProduct(@PathVariable("id") long id, @Valid@ModelAttribute("editProduct") EditProductValidateModel editProductValidateModel, 
			BindingResult bindingResult) throws Exception {
		if(bindingResult.hasErrors()) {
			ProductViewModel productViewModel = this.productService.findProductById(id);
			List<String> allCategories = this.productService.getAllCategories();
			List<String> productCategories = productViewModel.getCategories().stream().map(cat -> cat.getCategory()).collect(Collectors.toList());
			
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("allCategories", allCategories);
			modelAndView.addObject("productCategories", productCategories);
			modelAndView.addObject("product", productViewModel);
			modelAndView.setViewName(EDIT_PRODUCT_VIEW);
			return modelAndView;
		}
		
		try {
			this.productService.editProduct(editProductValidateModel, id);
		} catch (Exception e) {
			System.out.println(e.getMessage()); // intercept message 
		}
		return new ModelAndView("redirect:/product/all");
	}
	
	
	
	@GetMapping("/delete/{id}")
	public ModelAndView getDeleteProductView(@PathVariable("id") long id, ModelAndView modelAndView) {
		ProductViewModel productViewModel = null;
		try {
			productViewModel = this.productService.getProductById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage()); // Intercept message
		}
		
		modelAndView.addObject("product", productViewModel);
		modelAndView.setViewName(DELETE_PRODUCT_VIEW);
		return modelAndView;
	}
	
	
	@PostMapping("/delete/{id}")
	public ModelAndView confirmDeleteProduct(@PathVariable("id") long id) {
		try {
			this.productService.deleteProductById(id);
		} catch (Exception e) {
			System.out.println(e.getMessage()); // intercept exception
		}
		return new ModelAndView("redirect:/products/all");
	}
	
	
	
	
	
}

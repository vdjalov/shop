package app.web.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import app.data.models.Product;
import app.service.ProductService;

@RestController
@RequestMapping("/api/products/")
public class ProductsApi {

	private ProductService productService;
	
	@Autowired
	public ProductsApi(ProductService productService) {
		this.productService = productService;
	}


	@GetMapping(value = "/all", produces = "application/json")
	public Page<Product> findAllProducts(@RequestParam Optional<Integer> page, 
										 @RequestParam Optional<String> sortBy,
										 @RequestParam Optional<Integer> ipp) {
	
		Page<Product> users =  this.productService.findAllProducts(page, sortBy, ipp);
		return users;
		
	}
	

	@GetMapping(value = "/byCategory", produces = "application/json")
	public Page<Product> findAllProductsByCategory(
										 @RequestParam Optional<Integer> page, 
										 @RequestParam Optional<String> sortBy,
										 @RequestParam Optional<Integer> size,
										 @RequestParam Optional<String> cat) {
		Page<Product> products = null;
		if(cat.get().equals("all")) {
			products = this.productService.findAllProducts(page, sortBy, size);
		} else {
			try {
				products = this.productService.findProductsByCategory(page, sortBy, size, cat);
			} catch (Exception e) {
				System.out.println(e.getMessage()); // Intercept exception 
			}
		}
		  
		return products;
		
	}
	
}

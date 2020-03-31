package app.web.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.service.CartService;

@Controller
@RequestMapping("/carts")
public class CartController {

	private CartService cartService;
	
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}



	@PostMapping("/add/{productId}")
	public ModelAndView addToCart(@PathVariable("productId") long productId, @ModelAttribute("quantity") int quantity, Principal principal) {
		String username = principal.getName();
		
		try {
			this.cartService.saveToCart(productId, username, quantity);
		} catch (Exception e) {
			System.out.println(e.getMessage()); // 
		}
		return new ModelAndView("redirect:/orders/all");
	}
	
	
}

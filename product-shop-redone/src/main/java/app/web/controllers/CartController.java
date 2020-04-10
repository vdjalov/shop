package app.web.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.service.CartService;
import app.web.models.OrderViewModel;

@Controller
@RequestMapping("/cart")
public class CartController {

	public static final String CART_DETAILS_VIEW = "cartTemplates/cart-details";
	
	
	private CartService cartService;
	
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}


	@PostMapping("/add/{productId}")
	public ModelAndView addToCart(@PathVariable("productId") long productId, @ModelAttribute("quantity") int quantity, Principal principal) throws Exception {
		String username = principal.getName();
		
//		try {
			this.cartService.saveToCart(productId, username, quantity);
//		} catch (Exception e) {
//			System.out.println(e.getMessage()); // 
//		}
		return new ModelAndView("redirect:/cart/all");
	}
	
	
	@GetMapping("/all")
	public ModelAndView getCartDetailsView(ModelAndView modelAndView, Principal principal) {
		String username = principal.getName();
		List<OrderViewModel> orders = this.cartService.getAllUncheckedOrders(username);
		double totalCost = orders.stream().mapToDouble(value -> value.getTotalPrice()).sum();
		modelAndView.addObject("totalCost", totalCost);
		modelAndView.addObject("orders", orders);
		modelAndView.setViewName(CART_DETAILS_VIEW);
		return modelAndView;
	}
	
	
	@PostMapping("/remove/{id}")
	public ModelAndView deleteOrder(@PathVariable("id") long id) {
		this.cartService.deleteOrder(id);
		return new ModelAndView("redirect:/cart/all");
	}
	
	
	@PostMapping("/checkout")
	public ModelAndView checkoutOrders(ModelAndView modelAndView, Principal principal, RedirectAttributes redir) throws Exception {
		String username = principal.getName();
//		try {
			this.cartService.checkoutCart(username);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		redir.addFlashAttribute("msg", "your order is on its way");
		return new ModelAndView("redirect:/cart/all");
	}
	
	
	@ExceptionHandler(Exception.class)
	  public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		ModelAndView mav = new ModelAndView();
	 
	    mav.addObject("ex", ex);
	    mav.addObject("url", req.getRequestURL());
	    mav.setViewName("error/commonError");
	    return mav;
	  }
}





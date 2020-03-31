package app.web.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import app.service.OrderService;
import app.service.ProductService;
import app.web.models.OrderViewModel;
import app.web.models.ProductViewModel;

@Controller
@RequestMapping("/orders")
public class OrderController {

	public static final String ORDER_PRODUCT_VIEW = "orderTemplates/order-product";
	public static final String ALL_ORDERS_VIEW = "orderTemplates/orders-all";
	public static final String MY_ORDERS_VIEW = "orderTemplates/orders-my";
	
	private ProductService productService;
	private OrderService orderService;
	
	@Autowired
	public OrderController(ProductService productService, OrderService orderService) {
		this.productService = productService;
		this.orderService = orderService;
	}
	
	
	@GetMapping("/details/{productId}")
	public ModelAndView getOrderProductView(@PathVariable("productId") Long productId ,ModelAndView modelAndView, Principal principal) {
		ProductViewModel product = this.productService.findProductById(productId);
		String customer = principal.getName();
		modelAndView.addObject("customer", customer);
		modelAndView.addObject("product", product);
		modelAndView.setViewName(ORDER_PRODUCT_VIEW);
		return modelAndView;
	}
	
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN', 'ROOT')")
	public ModelAndView getAllOrdersView(ModelAndView modelAndView) {
		List<OrderViewModel> orders = this.orderService.getAllOrders();
		modelAndView.addObject("orders", orders);
		modelAndView.setViewName(ALL_ORDERS_VIEW);
		return modelAndView;
	}
	
	
	@GetMapping("/my")
	public ModelAndView getMyOrdersView(ModelAndView modelAndView, Principal principal) {
		String username = principal.getName();
		List<OrderViewModel> orders = this.orderService.getOrdersByCustomerName(username);
		modelAndView.addObject("orders", orders);
		modelAndView.setViewName(MY_ORDERS_VIEW);
		return modelAndView;
	}


	
}



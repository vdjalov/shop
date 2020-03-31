package app.service.implementations;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.models.Order;
import app.data.models.Product;
import app.data.models.User;
import app.data.repositories.OrderRepository;
import app.data.repositories.ProductRepository;
import app.data.repositories.UserRepository;
import app.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	private OrderRepository orderRepository;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	
	
	@Autowired
	public CartServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
			ProductRepository productRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
	}


	@Override
	public void saveToCart(long productId, String username, int quantity) throws Exception {
		User user = (User) this.userRepository.findByUsername(username);
		Optional<Product> product = this.productRepository.findById(productId);
		
		
		if(user!= null && product.isPresent()) {
			Order order = new Order();
			double totalPrice = this.calculateTotalPrice(product.get().getPrice(), quantity);
			order.setProduct(product.get());
			order.setUser(user);
			order.setQuantity(quantity);
			order.setTotalPrice(totalPrice);
			order.setOrderDate(new Date());
			orderRepository.save(order);
		} else {
			throw new Exception("username or product id invalid");
		}
	}


	private double calculateTotalPrice(double price, int quantity) {
		return price * quantity;
	}

	
	
}









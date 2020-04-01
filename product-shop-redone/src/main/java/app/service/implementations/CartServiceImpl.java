package app.service.implementations;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.models.Order;
import app.data.models.Product;
import app.data.models.User;
import app.data.repositories.OrderRepository;
import app.data.repositories.ProductRepository;
import app.data.repositories.UserRepository;
import app.service.CartService;
import app.web.models.OrderViewModel;

@Service
public class CartServiceImpl implements CartService {

	private OrderRepository orderRepository;
	private UserRepository userRepository;
	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public CartServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
			ProductRepository productRepository, ModelMapper modelMapper) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.modelMapper = modelMapper;
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


	@Override
	public List<OrderViewModel> getAllUncheckedOrders(String username) {
		long customerId = this.userRepository.getByUsername(username).get().getId();
		List<OrderViewModel> orders = this.orderRepository.findAllUncheckedOrdersByCustomerId(customerId)
														  .stream()
														  .map(order -> this.modelMapper.map(order, OrderViewModel.class))
														  .collect(Collectors.toList());
		return orders;
	}


	@Override
	public void deleteOrder(long id) {
		this.orderRepository.deleteById(id);
		
	}


	@Override
	public void checkoutCart(String username) throws Exception {
		if(this.userRepository.getByUsername(username).isPresent()) {
			long customerId = this.userRepository.getByUsername(username).get().getId();
			List<Order> orders = this.orderRepository.findAllUncheckedOrdersByCustomerId(customerId);
			orders.forEach(order -> {
				order.setPaid(true);
				this.orderRepository.saveAndFlush(order);
			});
		} else {
			throw new Exception("ivalid order id or order does not exist");
		}
	}

	
	
}









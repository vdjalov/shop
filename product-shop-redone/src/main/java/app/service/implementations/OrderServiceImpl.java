package app.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.models.Order;
import app.data.repositories.OrderRepository;
import app.service.OrderService;
import app.web.models.OrderViewModel;

@Service
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepository;
	private ModelMapper modelMapper;
	
	
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
		this.orderRepository = orderRepository;
		this.modelMapper = modelMapper;
	}


	@Override
	public List<OrderViewModel> getAllOrders() {
		return this.orderRepository
						.findAll().stream()
						.map(order-> this.modelMapper.map(order, OrderViewModel.class))
						.collect(Collectors.toList());
	}


	@Override
	public List<OrderViewModel> getOrdersByCustomerName(String username) {
		return this.orderRepository.findAll().stream()
								   .filter(order -> order.getCustomer().getUsername().equals(username))
								   .map(order -> this.modelMapper.map(order, OrderViewModel.class))
								   .collect(Collectors.toList());
	}


	@Override
	public OrderViewModel getOrderById(long orderId) throws Exception {
		Optional<Order> order = this.orderRepository.findById(orderId);
		OrderViewModel orderViewModel = null;
		if(order.isPresent()) {
			orderViewModel = this.modelMapper.map(order.get(), OrderViewModel.class);
		} else {
			throw new Exception("order id wrong or order unavailable");
		}
		
		return orderViewModel;
	}							   

	
	
	
}

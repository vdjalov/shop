package app.service;

import java.util.List;

import app.web.models.OrderViewModel;

public interface OrderService {

	List<OrderViewModel> getAllOrders();

	List<OrderViewModel> getOrdersByCustomerName(String username);

	OrderViewModel getOrderById(long orderId) throws Exception;

}

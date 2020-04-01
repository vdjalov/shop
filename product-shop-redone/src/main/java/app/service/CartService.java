package app.service;

import java.util.List;

import app.web.models.OrderViewModel;

public interface CartService {

	void saveToCart(long productId, String username, int quantity) throws Exception;

	List<OrderViewModel> getAllUncheckedOrders(String username);

	void deleteOrder(long id);

	void checkoutCart(String username) throws Exception;

}

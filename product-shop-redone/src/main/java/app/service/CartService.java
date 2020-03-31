package app.service;

public interface CartService {

	void saveToCart(long productId, String username, int quantity) throws Exception;

}

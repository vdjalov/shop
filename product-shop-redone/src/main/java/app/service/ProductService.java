package app.service;


import java.io.IOException;
import java.util.Optional;

import org.springframework.data.domain.Page;

import app.data.models.Product;
import app.service.models.AddProductModel;

public interface ProductService {

	void addProduct(AddProductModel addProductModel) throws IOException;

	Page<Product> findAllUsers(Optional<Integer> page, Optional<String> sortBy, Optional<Integer> itemsPerPage);

}

package app.service;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import app.data.models.Product;
import app.service.models.AddProductModel;
import app.service.models.EditProductValidateModel;
import app.web.models.ProductViewModel;

public interface ProductService {

	void addProduct(AddProductModel addProductModel) throws IOException;

	Page<Product> findAllProducts(Optional<Integer> page, Optional<String> sortBy, Optional<Integer> itemsPerPage);

	ProductViewModel findProductById(Long id);

	List<String> getAllCategories();

	void editProduct(EditProductValidateModel editProductValidateModel, long id) throws Exception;

	ProductViewModel getProductById(long id) throws Exception;

	void deleteProductById(long id) throws Exception;

	Page<Product> findProductsByCategory(Optional<Integer> page, Optional<String> sortBy, Optional<Integer> size,
			Optional<String> cat);

}

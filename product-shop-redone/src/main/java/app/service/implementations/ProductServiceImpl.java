package app.service.implementations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data.models.Category;
import app.data.models.Product;
import app.data.repositories.ProductRepository;
import app.service.CategoryService;
import app.service.CloudinaryService;
import app.service.ProductService;
import app.service.models.AddProductModel;
import app.service.models.EditProductValidateModel;
import app.web.models.ProductViewModel;

@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	private CategoryService categoryService;
	private ModelMapper modelMapper;
	private CloudinaryService cloudinaryService;
	
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper,
			CloudinaryService cloudinaryService) {
		this.productRepository = productRepository;
		this.categoryService = categoryService;
		this.modelMapper = modelMapper;
		this.cloudinaryService = cloudinaryService;
	}


	@Override
	public void addProduct(AddProductModel addProductModel) throws IOException {
		MultipartFile multipartFile = addProductModel.getImage();
		String url = this.cloudinaryService.uploadImage(multipartFile);
		Product product = this.modelMapper.map(addProductModel, Product.class);
		product.setImageUrl(url);
		
		this.productRepository.save(product);
		
	}


	@Override
	public Page<Product> findAllProducts(Optional<Integer> page, Optional<String> sortBy, Optional<Integer> ipp) {
		Direction direction = Direction.ASC;
		Optional<String> sort = null;
		
		if(sortBy.isPresent() && !sortBy.get().equals("name")) {
			String result[] = sortBy.get().split("-");
			sort = Optional.of(result[0]);
				if(result[1].equals("desc")) {
					direction = Direction.DESC;
				}
		} else {
			sort= Optional.of("name");
		}
		
		Page<Product> products = this.productRepository.findAll(PageRequest.of(page.orElse(0), ipp.orElse(5),
				Sort.by(direction, sort.orElse("name"))));
		return products;
	}


	@Override
	public ProductViewModel findProductById(Long id) {
	Optional<Product> product = this.productRepository.findById(id);
	ProductViewModel productViewModel = null;
		if(product.isPresent()) {
			productViewModel = this.modelMapper.map(product.get(), ProductViewModel.class);
		}
		return productViewModel;
	}


	@Override
	public List<String> getAllCategories() {
		List<String> allCategories = new ArrayList<>();
				this.categoryService.getAllCategories().stream()
									.forEach(category -> allCategories.add(category.getCategory()));
		return allCategories;
	}


	@Override
	public void editProduct(EditProductValidateModel editProductValidateModel, long id) throws Exception {
		List<Category> productCategories = new ArrayList<>();
		editProductValidateModel.getCategories().stream()
								.forEach(cat -> {
									Optional<Category> category = this.categoryService.getByCategoryName(cat);
									if(category.isPresent()) {
										productCategories.add(category.get());
									}
								});
		Optional<Product> product = this.productRepository.findById(id);
		if(product.isPresent()) {
			product.get().setCategories(productCategories);
			product.get().setDescription(editProductValidateModel.getDescription());
			product.get().setName(editProductValidateModel.getName());
			product.get().setPrice(editProductValidateModel.getPrice());
			this.productRepository.save(product.get());
		} else {
			throw new Exception("Category not present");
		}
	}


	@Override
	public ProductViewModel getProductById(long id) throws Exception {
		Optional<Product> product = this.productRepository.findById(id);
		ProductViewModel productViewModel = null;	
			if(product.isPresent()) {
				productViewModel = this.modelMapper.map(product.get(), ProductViewModel.class);
			} else {
				throw new Exception("product not found");
			}
		return productViewModel;
	}


	@Override
	public void deleteProductById(long id) throws Exception {
		if(this.productRepository.findById(id).isPresent()) {
			this.productRepository.deleteById(id);
		} else {
			throw new Exception("Cannot delete product. Id not found.");
		}
	}

	
	@Override
	public Page<Product> findProductsByCategory(Optional<Integer> page, Optional<String> sortBy, Optional<Integer> size,
			Optional<String> cat) {
	
		Page<Product> allProducts = this.productRepository.getAllProductsByCategory(PageRequest.of(page.orElse(0), size.orElse(5),
				Sort.by(Direction.ASC, sortBy.orElse("name"))), cat.get());
		return allProducts;
	}

	
	
	
	
}







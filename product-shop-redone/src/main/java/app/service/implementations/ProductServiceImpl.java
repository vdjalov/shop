package app.service.implementations;

import java.io.IOException;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.data.models.Product;
import app.data.models.User;
import app.data.repositories.ProductRepository;
import app.service.CloudinaryService;
import app.service.ProductService;
import app.service.models.AddProductModel;

@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	private ModelMapper modelMapper;
	private CloudinaryService cloudinaryService;
	
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,
			CloudinaryService cloudinaryService) {
		this.productRepository = productRepository;
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
	public Page<Product> findAllUsers(Optional<Integer> page, Optional<String> sortBy) {
		// Parse sort by to chnage the direction
		Page<Product> products = this.productRepository.findAll(PageRequest.of(page.orElse(0), 5,
				Sort.by(Direction.ASC, sortBy.orElse("id"))));
		return products;
	}

	
	
	
	
}







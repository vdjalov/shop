package app.service.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.data.models.Category;
import app.data.repositories.CategoryRepository;
import app.service.CategoryService;
import app.service.models.AddCategoryModel;
import app.web.models.CategoryViewModel;

@Service
public class CategoryServiceImpl implements CategoryService {

	
	private CategoryRepository categoryRepository;
	private ModelMapper modelMapper;
	
	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
		this.categoryRepository = categoryRepository;
		this.modelMapper = modelMapper;
	}

	
	@Override
	public void addCategory(AddCategoryModel addCategoryModel) {
		Category category = this.modelMapper.map(addCategoryModel, Category.class);
		this.categoryRepository.save(category);
	}


	@Override
	public List<CategoryViewModel> getAllCategories() {
		return this.categoryRepository.findAll().stream()
									  .map(category -> this.modelMapper.map(category, CategoryViewModel.class)).collect(Collectors.toList());
	}


	@Override
	public CategoryViewModel getByCategoryId(long categoryId) throws Exception {
		CategoryViewModel categoryViewModel = null;
		Optional<Category> category =  this.categoryRepository.findById(categoryId);
			if(category.isPresent()) {
				categoryViewModel = this.modelMapper.map(category.get(), CategoryViewModel.class);
			} else {
				throw new Exception("category does not exist");
			}
		return categoryViewModel;
	}


	@Override
	public CategoryViewModel confirmCategoryChanges(long categoryId, String categoryName) throws Exception {
		Optional<Category> category = this.categoryRepository.findById(categoryId);
		if(category.isPresent()) {
			if(categoryName.isBlank() || categoryName.length() > 50) {
				throw new Exception("category name is either empty or longer than 50 chars");
			}
			category.get().setCategory(categoryName);
			this.categoryRepository.save(category.get());
		} else {
			throw new Exception("wrong category id");
		}
		CategoryViewModel categoryViewModel = this.modelMapper.map(category.get(), CategoryViewModel.class);
		return categoryViewModel;
	}


	@Override
	public CategoryViewModel deleteCategoryById(long categoryId) throws Exception {
		CategoryViewModel categoryViewModel = null;
		if(categoryRepository.findById(categoryId).isPresent()) {
			categoryViewModel = this.modelMapper.map(this.categoryRepository.findById(categoryId).get(), CategoryViewModel.class);
			this.categoryRepository.deleteById(categoryId);
		} else {
			throw new Exception("wrong id");
		}
	
		return categoryViewModel;
	}


	
	
	
	
	
	
}

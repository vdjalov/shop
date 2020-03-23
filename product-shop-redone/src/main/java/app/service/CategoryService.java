package app.service;

import java.util.List;

import javax.validation.Valid;

import app.service.models.AddCategoryModel;
import app.web.models.CategoryViewModel;

public interface CategoryService {

	void addCategory(@Valid AddCategoryModel addCategoryModel);

	List<CategoryViewModel> getAllCategories();

	CategoryViewModel getByCategoryId(long categoryId) throws Exception;

	CategoryViewModel confirmCategoryChanges(long categoryId, String categoryName) throws Exception;

	CategoryViewModel deleteCategoryById(long categoryId) throws Exception;

}

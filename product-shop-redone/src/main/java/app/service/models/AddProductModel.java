package app.service.models;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import app.data.models.Category;

public class AddProductModel {

	@NotEmpty(message = "name cannot be empty")
	private String name;
	
	@Size(max = 120,min = 0, message = "size must be between zero(0) and one hundred and twenty(120) characters")
	private String description;
	
	@DecimalMin(value = "0.00")
	private double price;
	
	private MultipartFile image;
	
	private List<Category> categories;
	
	public AddProductModel() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
	
	
}

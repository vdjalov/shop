package app.service.models;

import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class EditProductValidateModel {

	@NotEmpty(message = "Cannot be empty")
	private String name;
	
	@Size(max = 120,min = 0, message = "Size must be between zero(0) and one hundered and twenty(120) characters")
	private String description;
	
	@DecimalMin(value = "0.00")
	private double price;
	
	private List<String> categories;
	
	public EditProductValidateModel() {
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

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	


	
	
	
	
	
	
}

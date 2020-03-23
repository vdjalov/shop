package app.service.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AddCategoryModel {

	@NotEmpty(message = "category empty")
	@Size(min = 1, max = 50, message = "min size 1, max size 50 letters")
	private String category;

	public AddCategoryModel() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}

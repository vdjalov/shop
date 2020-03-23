package app.web.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CategoryViewModel {

	
	private long id;
	
	@NotEmpty(message = "category empty")
	@Size(min = 1, max = 50, message = "min size 1, max size 50 letters")
	private String category;
	
	public CategoryViewModel() {
	}
	public CategoryViewModel(long id, String category) {
		this.id = id;
		this.category = category;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}

package app.data.models;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "catgories")
public class Category extends BaseEntity {

	@NotEmpty(message = "category empty")
	private String category;

	public Category() {
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	
}

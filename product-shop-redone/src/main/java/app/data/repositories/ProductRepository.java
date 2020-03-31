package app.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.data.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query(value = "select * from products", nativeQuery = true)
	Page<Product> getAllProducts(Pageable pageable);
	
	@Query(value = "select * from products as p join product_category as pc on p.id = pc.product_id join catgories as c on pc.category_id = c.id where c.category = :cat", 
			nativeQuery = true)
	Page<Product> getAllProductsByCategory(Pageable pageable, @Param("cat") String cat);
	
}

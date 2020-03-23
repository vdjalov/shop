package app.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.data.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}

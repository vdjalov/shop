package app.data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.data.models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query(value = "select * from orders as o where o.customer_id= :customerId and o.is_paid= 0" ,nativeQuery = true)
	List<Order> findAllUncheckedOrdersByCustomerId(@Param("customerId") long customerId);

}

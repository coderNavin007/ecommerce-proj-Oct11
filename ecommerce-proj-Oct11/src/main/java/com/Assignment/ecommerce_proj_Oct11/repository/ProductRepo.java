package com.Assignment.ecommerce_proj_Oct11.repository;

import com.Assignment.ecommerce_proj_Oct11.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    //JPQL JPA Query Language
    @Query("SELECT p from Product p WHERE "
            +"LOWER(p.name) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "
            +"LOWER(p.description) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "
    +"LOWER(p.category) LIKE LOWER(CONCAT('%',:keyword,'%')) OR "
    +"LOWER(p.brand) LIKE LOWER(CONCAT('%',:keyword,'%')) ")
    List<Product> searchProducts(String keyword);
}

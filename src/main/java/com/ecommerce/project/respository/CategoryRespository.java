package com.ecommerce.project.respository;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRespository extends JpaRepository<Category,Long> {
}

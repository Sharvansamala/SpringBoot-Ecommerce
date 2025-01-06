package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
    List<Category> categories = new ArrayList<>();
    private Long nextID = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextID++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"));
//        if (category == null) return "Category Not found";
        categories.remove(category);
        return "Category with categoryId: " + categoryId + " deleted Successfully";

    }

    @Override
    public Category updateCategory(Category category,Long categoryId) {
        Optional<Category> optionalCategory= categories.stream()
                 .filter(category1 -> category1.getCategoryId().equals(categoryId))
                 .findFirst();
        if(optionalCategory.isPresent()){
            Category existingCategoty = optionalCategory.get();
            existingCategoty.setCategoryName(category.getCategoryName());
            return existingCategoty;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found in db");
        }
//        return null;
    }
}

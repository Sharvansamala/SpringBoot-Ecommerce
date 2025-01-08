package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.respository.CategoryRespository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImplementation implements CategoryService {
//    List<Category> categories = new ArrayList<>();
//    private Long nextID = 1L;

    private CategoryRespository categoryRespository;

    public CategoryServiceImplementation(CategoryRespository categoryRespository) {
        this.categoryRespository = categoryRespository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRespository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRespository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Optional<Category> categories = categoryRespository.findById(categoryId);
        Category category = categories
                .stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"));
//        if (category == null) return "Category Not found";
        categoryRespository.delete(category);
        return "Category with categoryId: " + categoryId + " deleted Successfully";

    }

    @Override
    public Category updateCategory(Category category,Long categoryId) {
//        Optional<Category> optionalCategory= categories.stream()
//                 .filter(category1 -> category1.getCategoryId().equals(categoryId))
//                 .findFirst();
        Optional<Category> optionalCategory = categoryRespository.findById(categoryId);
        optionalCategory
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found in DB"));
        Category existingCategoty = optionalCategory.get();
        existingCategoty.setCategoryName(category.getCategoryName());
        Category savedCategory = categoryRespository.save(existingCategoty);
//        return savedCategory;
        //        return null;

        Category category1= categoryRespository.findById(categoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found in DB"));
        category1.setCategoryName(category.getCategoryName());
        return categoryRespository.save(category1);
    }
}

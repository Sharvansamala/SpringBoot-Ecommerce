package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.respository.CategoryRespository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRespository categoryRespository;
    private final ModelMapper modelMapper;

    public CategoryServiceImplementation(CategoryRespository categoryRespository, ModelMapper modelMapper) {
        this.categoryRespository = categoryRespository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize,sortByAndOrder);
        Page<Category> categoryPage  = categoryRespository.findAll(pageDetails);
        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("Category List is empty");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
        return new CategoryResponse(categoryDTOS,categoryPage.getNumber()
                ,categoryPage.getSize(),categoryPage.getTotalElements(),
                categoryPage.getTotalPages(),categoryPage.isLast());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRespository.findByCategoryName(category.getCategoryName());
        if (savedCategory == null) {
            return modelMapper.map(categoryRespository.save(category), CategoryDTO.class);
        } else
            throw new APIException("Category with name " + category.getCategoryName() + " present at id: " + savedCategory.getCategoryId());
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
//        Optional<Category> categories = categoryRespository.findById(categoryId);
//        Category category = categories
//                .stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
//                .findFirst()
////                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category Not Found"));
//                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
////        if (category == null) return "Category Not found"
//        categoryRespository.delete(category);

        Category category = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new APIException("Category Not found in database"));
        categoryRespository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);

    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
//        Optional<Category> optionalCategory= categories.stream()
//                 .filter(category1 -> category1.getCategoryId().equals(categoryId))
//                 .findFirst();
//        Optional<Category> optionalCategory = categoryRespository.findById(categoryId);
//        optionalCategory
//                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not Found in DB"));
//                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
//        Category existingCategory = optionalCategory.get();
//        existingCategory.setCategoryName(category.getCategoryName());
//        Category savedCategory = categoryRespository.save(existingCategory);
//        return savedCategory;
        //        return null;
        Category category1 = categoryRespository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found in DB"));
        Category category = modelMapper.map(categoryDTO, Category.class);
        category1.setCategoryName(category.getCategoryName());
        Category savedCategory = categoryRespository.save(category1);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}

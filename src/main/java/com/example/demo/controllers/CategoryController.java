package com.example.demo.controllers;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.CategoryDto;
import com.example.demo.services.CategoryServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("/api/categories/")
@Tag(name="Categories Controller", description = "APIs for Category Management")
public class CategoryController {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private ModelMapper modelMapper;

    //    POST-create Category
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(createdCategory);
    }

    //    PUT - update category
    @PreAuthorize("hasRole(ADMIN)")
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Integer categoryId, @RequestBody CategoryDto categoryDto) {
        CategoryDto category = this.categoryService.updateCategory(categoryDto, categoryId);
        return ResponseEntity.ok(category);
    }

    //    DELETE - delete category
    @PreAuthorize("hasRole(ADMIN)")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        try {
            this.categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(new ApiResponse("deleted", true));
        } catch (Exception ignored) {
            return new ResponseEntity<>(new ApiResponse("deleted", false), HttpStatus.NOT_FOUND);
        }
    }

    //    GET - get specific category
    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") int categoryId) {
        try {
            return new ResponseEntity<>(this.categoryService.getCategory(categoryId), FOUND);
        } catch (Exception e) {
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        }
    }

    //    GET - get all categories
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return new ResponseEntity<>(this.categoryService.getAllCategories(), HttpStatus.OK);
    }


}

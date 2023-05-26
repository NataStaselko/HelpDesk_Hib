package com.final_project.staselko.controller;

import com.final_project.staselko.model.entity.Category;
import com.final_project.staselko.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoriesService categoriesService;

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        List<Category> list = categoriesService.getAllCategories();
        return ResponseEntity.ok(list);
    }
}

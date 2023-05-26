package com.final_project.staselko.dao;

import com.final_project.staselko.model.entity.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> findAll();
}

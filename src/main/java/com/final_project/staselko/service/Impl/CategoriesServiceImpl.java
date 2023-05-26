package com.final_project.staselko.service.Impl;

import com.final_project.staselko.dao.CategoryDao;
import com.final_project.staselko.model.entity.Category;
import com.final_project.staselko.service.CategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }
}
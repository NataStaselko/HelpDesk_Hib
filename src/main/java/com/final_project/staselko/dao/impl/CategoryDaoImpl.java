package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.CategoryDao;
import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.model.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDaoImpl extends HibernateDao<Category> implements CategoryDao {

    public CategoryDaoImpl() {
        setModelClass(Category.class);
    }

    @Override
    public List<Category> findAll(){
        return findAllEntities();
    }
}

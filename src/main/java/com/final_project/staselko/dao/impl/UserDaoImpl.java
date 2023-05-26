package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.dao.UserDao;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Role;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl extends HibernateDao<User> implements UserDao {

    public UserDaoImpl() {
        setModelClass(User.class);
    }

    @Override
    public Optional<User> findByEmail(String param, String email) {
        return Optional.ofNullable(getEntity(param, email));
    }

    @Override
    public Optional<User> findById(Long userId) {
        return findEntityById(userId);
    }

    @Override
    public List<User> findAllByRole(String param, Role role) {
        return getAllEntity(param, role);
    }
}

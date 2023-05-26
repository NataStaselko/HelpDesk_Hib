package com.final_project.staselko.dao;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Role;

import java.util.List;
import java.util.Optional;


public interface UserDao {
    Optional<User> findByEmail(String param, String email);
    Optional<User> findById(Long userId);
    List<User> findAllByRole(String param, Role role);
}

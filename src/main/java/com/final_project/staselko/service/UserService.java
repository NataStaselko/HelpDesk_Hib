package com.final_project.staselko.service;

import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Role;

import java.util.List;

public interface UserService {
    List<User> getAllUsersByRole(Role role);
    User getUserById(Long userId);
}

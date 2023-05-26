package com.final_project.staselko.service.Impl;

import com.final_project.staselko.dao.UserDao;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Role;
import com.final_project.staselko.model.exception.ResourceNotFoundException;
import com.final_project.staselko.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    @Transactional
    @Override
    public List<User> getAllUsersByRole(Role role) {
        return userDao.findAllByRole("role", role);
    }
    @Transactional
    @Override
    public User getUserById(Long userId) {
        return userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
    }
}

package com.final_project.staselko.converter;

import com.final_project.staselko.model.dto.UserDto;
import com.final_project.staselko.model.entity.User;

public interface UserConverter {
    User toUser (UserDto userDto);
    UserDto toUserDto(User user);
}

package com.final_project.staselko.model.dto;

import com.final_project.staselko.utils.annotations.TextValid;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CommentDto {

    private Long id;

    @TextValid
    @Size(max = 500, message = "The text exceed the 500 character limit")

    private String text;
    private String date;
    private UserDto userDto;
}

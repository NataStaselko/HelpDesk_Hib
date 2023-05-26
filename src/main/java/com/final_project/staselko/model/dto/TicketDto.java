package com.final_project.staselko.model.dto;

import com.final_project.staselko.model.entity.Category;
import com.final_project.staselko.utils.annotations.DateValid;
import com.final_project.staselko.utils.annotations.TextValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;

    @TextValid
    @NotBlank(message = "The Name cannot be empty")
    private String name;

    @TextValid
    @Size(max = 500, message = "The text exceed the 500 character limit")
    private String description;

    private String created_on;

    @DateValid
    private String desired_date;

    @NotNull(message = "The Category cannot be empty")
    private Category category;

    @NotEmpty(message = "The Urgency cannot be empty")
    private String urgency;

    private String state;

    private UserDto owner;

    private UserDto approved;

    private UserDto assignee;

    private List<String> actions = new ArrayList<>();

}

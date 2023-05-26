package com.final_project.staselko.model.dto;

import com.final_project.staselko.utils.annotations.TextValid;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class FeedbackDto {
    private Integer rate;

    @TextValid
    @Size(max = 500, message = "The text exceed the 500 character limit")
    private String text;
}

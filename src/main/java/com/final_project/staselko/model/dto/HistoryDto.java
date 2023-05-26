package com.final_project.staselko.model.dto;

import lombok.Data;

@Data
public class HistoryDto {
    private String date;
    private UserDto userDto;
    private String action;
    private String description;
}

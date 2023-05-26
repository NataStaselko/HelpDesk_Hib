package com.final_project.staselko.converter;
import com.final_project.staselko.model.dto.HistoryDto;
import com.final_project.staselko.model.entity.History;

public interface HistoryConverter {

    HistoryDto toHistoryDto(History history);
}

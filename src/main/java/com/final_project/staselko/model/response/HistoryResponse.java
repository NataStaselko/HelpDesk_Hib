package com.final_project.staselko.model.response;

import com.final_project.staselko.model.dto.HistoryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private List<HistoryDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
}

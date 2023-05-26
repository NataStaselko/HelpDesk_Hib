package com.final_project.staselko.converter.impl;

import com.final_project.staselko.converter.HistoryConverter;
import com.final_project.staselko.converter.UserConverter;
import com.final_project.staselko.model.dto.HistoryDto;
import com.final_project.staselko.model.entity.History;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class HistoryConverterImpl implements HistoryConverter {
    private final UserConverter userConverter;


    @Override
    public HistoryDto toHistoryDto(History history) {
        HistoryDto historyDto = new HistoryDto();
        historyDto.setDate(history.getDate().format(DateTimeFormatter
                .ofPattern("MMM dd, yyyy HH:mm:ss").localizedBy(Locale.ENGLISH)));
        historyDto.setUserDto(userConverter.toUserDto(history.getUser()));
        historyDto.setAction(history.getAction());
        historyDto.setDescription(history.getDescription());
        return historyDto;
    }
}

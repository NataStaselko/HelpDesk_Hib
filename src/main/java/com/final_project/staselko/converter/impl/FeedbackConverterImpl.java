package com.final_project.staselko.converter.impl;

import com.final_project.staselko.converter.FeedbackConverter;
import com.final_project.staselko.model.dto.FeedbackDto;
import com.final_project.staselko.model.entity.Feedback;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedbackConverterImpl implements FeedbackConverter {
    @Override
    public Feedback toFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setRate(feedbackDto.getRate());
        feedback.setText(feedbackDto.getText());
        return feedback;
    }

    @Override
    public FeedbackDto toFeedbackDto(Feedback feedback) {
        FeedbackDto feedbackDto = new FeedbackDto();
        feedbackDto.setRate(feedback.getRate());
        feedbackDto.setText(feedback.getText());
        return feedbackDto;
    }
}

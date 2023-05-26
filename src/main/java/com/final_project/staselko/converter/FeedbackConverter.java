package com.final_project.staselko.converter;

import com.final_project.staselko.model.dto.FeedbackDto;
import com.final_project.staselko.model.entity.Feedback;

public interface FeedbackConverter {
    Feedback toFeedback(FeedbackDto feedbackDto);

    FeedbackDto toFeedbackDto(Feedback feedback);
}

package com.final_project.staselko.service;

import com.final_project.staselko.model.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    void saveFeedback(FeedbackDto feedbackDto, Long ticketId);

    List<FeedbackDto> getFeedbackByTicket(Long ticketId);
}

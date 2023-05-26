package com.final_project.staselko.service.Impl;

import com.final_project.staselko.converter.FeedbackConverter;
import com.final_project.staselko.dao.FeedbackDao;
import com.final_project.staselko.model.dto.FeedbackDto;
import com.final_project.staselko.model.entity.Feedback;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.service.FeedbackService;
import com.final_project.staselko.service.TicketService;
import com.final_project.staselko.utils.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackDao feedbackDao;
    private final TicketService ticketService;
    private final UserProvider userProvider;
    private final FeedbackConverter feedbackConverter;
    @Transactional
    @Override
    public void saveFeedback(FeedbackDto feedbackDto, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        Feedback feedback = feedbackConverter.toFeedback(feedbackDto);
        feedback.setTicket(ticket);
        feedback.setUser(userProvider.getCurrentUser());
        feedbackDao.save(feedback);
    }
    @Transactional
    @Override
    public List<FeedbackDto> getFeedbackByTicket(Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        return feedbackDao.getFeedback("ticket", ticket).stream()
                .map(feedbackConverter::toFeedbackDto)
                .collect(Collectors.toList());
    }
}

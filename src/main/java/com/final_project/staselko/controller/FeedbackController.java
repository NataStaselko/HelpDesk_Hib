package com.final_project.staselko.controller;

import com.final_project.staselko.model.dto.FeedbackDto;
import com.final_project.staselko.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Void> saveFeedback(@RequestBody @Valid FeedbackDto feedbackDto,
                                             @RequestParam(value = "ticketId") Long ticketId){
        feedbackService.saveFeedback(feedbackDto, ticketId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<FeedbackDto>> getAllFeedbacks(@RequestParam(value = "ticketId") Long ticketId){
        List<FeedbackDto> list = feedbackService.getFeedbackByTicket(ticketId);
        return ResponseEntity.ok(list);
    }
}

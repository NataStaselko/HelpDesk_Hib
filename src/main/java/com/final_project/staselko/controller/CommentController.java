package com.final_project.staselko.controller;

import com.final_project.staselko.model.dto.CommentDto;
import com.final_project.staselko.model.response.CommentsResponse;
import com.final_project.staselko.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @Value("${pagination.page_size}")
    private String page_size;

    @PostMapping
    public ResponseEntity<Void> saveComment(@RequestBody @Valid CommentDto commentDto,
                                            @RequestParam(value = "ticketId") Long ticketId){
        commentService.saveComment(commentDto, ticketId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<CommentsResponse> getAllCommentByTicket(@RequestParam(value = "ticketId") Long ticketId,
                                                                  @RequestParam(value = "pageNo", defaultValue = "0",
                                                                          required = false) int pageNo){
        CommentsResponse comments = commentService.getComment(ticketId,pageNo, Integer.parseInt(page_size));
        return ResponseEntity.ok(comments);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable(value = "id") Long commentId,
                                              @RequestBody  CommentDto commentDto){
        commentService.changeComment(commentId, commentDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable(value = "id") Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}

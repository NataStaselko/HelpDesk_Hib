package com.final_project.staselko.service;

import com.final_project.staselko.model.dto.CommentDto;
import com.final_project.staselko.model.response.CommentsResponse;

public interface CommentService {
    void saveComment(CommentDto commentDto, Long ticketId);
    CommentsResponse getComment(Long ticketId, int pageNo, int pageSize);
    void changeComment(Long commentId,  CommentDto commentDto);

    void deleteComment(Long commentId);
}

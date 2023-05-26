package com.final_project.staselko.service.Impl;

import com.final_project.staselko.converter.CommentConverter;
import com.final_project.staselko.dao.CommentDao;
import com.final_project.staselko.model.dto.CommentDto;
import com.final_project.staselko.model.entity.Comment;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.exception.ResourceNotFoundException;
import com.final_project.staselko.model.response.CommentsResponse;
import com.final_project.staselko.service.CommentService;
import com.final_project.staselko.service.TicketService;
import com.final_project.staselko.utils.UserProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final TicketService ticketService;
    private final CommentDao commentDao;
    private final CommentConverter commentConverter;
    private final UserProvider userProvider;

    @Transactional
    @Override
    public void saveComment(CommentDto commentDto, Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        Comment comment = commentConverter.toComment(commentDto);
        comment.setTicket(ticket);
        comment.setUser(userProvider.getCurrentUser());
        commentDao.save(comment);
    }
    @Transactional
    @Override
    public CommentsResponse getComment(Long ticketId, int pageNo, int pageSize) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        String condition = " order by date desc";
        List<Comment> list = commentDao.findComments(pageNo, pageSize, condition, "ticket", ticket);
        long total = commentDao.getTotalComments("ticket", ticket, "");
        List<CommentDto> content = list.stream()
                .map(commentConverter::toCommentDto)
                .collect(Collectors.toList());
        CommentsResponse commentsResponse = new CommentsResponse();
        commentsResponse.setContent(content);
        commentsResponse.setPageNo(pageNo);
        commentsResponse.setPageSize(pageSize);
        commentsResponse.setTotalElements(total);
        int totalPages = 0;
        if (pageSize != 0) {
            if (total % pageSize == 0) {
                totalPages = (int) (total / pageSize);
            } else {
                totalPages = (int) (total / pageSize + 1);
            }
        }
        commentsResponse.setTotalPages(totalPages);
        return commentsResponse;
    }
    @Transactional
    @Override
    public void changeComment(Long commentId,  CommentDto commentDto) {
        Comment comment = commentDao.findCommentById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));
        comment.setText(commentDto.getText());
        commentDao.updateComment(comment);
    }
    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        Comment comment = commentDao.findCommentById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId));
        commentDao.delete(comment);
    }
}

package com.final_project.staselko.converter.impl;

import com.final_project.staselko.converter.CommentConverter;
import com.final_project.staselko.converter.UserConverter;
import com.final_project.staselko.model.dto.CommentDto;
import com.final_project.staselko.model.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CommentConverterImpl implements CommentConverter {
    private final UserConverter userConverter;

    @Override
    public Comment toComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        return comment;
    }

    @Override
    public CommentDto toCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setText(comment.getText());
        commentDto.setDate(comment.getDate().format(DateTimeFormatter
                .ofPattern("MMM dd, yyyy HH:mm:ss").localizedBy(Locale.ENGLISH)));
        commentDto.setUserDto(userConverter.toUserDto(comment.getUser()));
        return commentDto;
    }
}

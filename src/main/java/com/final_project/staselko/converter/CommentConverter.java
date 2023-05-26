package com.final_project.staselko.converter;

import com.final_project.staselko.model.dto.CommentDto;
import com.final_project.staselko.model.entity.Comment;

public interface CommentConverter {

    Comment toComment(CommentDto commentDto);

    CommentDto toCommentDto(Comment comment);
}

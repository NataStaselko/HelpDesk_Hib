package com.final_project.staselko.dao;

import com.final_project.staselko.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDao {
    void save(Comment comment);
    Optional <Comment> findCommentById(Long commentId);

    List<Comment> findComments(int pageNo, int pageSize, String condition,
                               String param, Object o);

    long getTotalComments(String param, Object o, String condition);

    void updateComment(Comment comment);

    void delete(Comment comment);

}

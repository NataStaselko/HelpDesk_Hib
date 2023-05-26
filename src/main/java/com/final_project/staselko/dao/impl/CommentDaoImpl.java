package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.CommentDao;
import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.model.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CommentDaoImpl extends HibernateDao<Comment> implements CommentDao {
    public CommentDaoImpl() {
        setModelClass(Comment.class);
    }

    @Override
    public void save(Comment comment) {
        create(comment);
    }

    @Override
    public Optional<Comment> findCommentById(Long commentId) {
        return findEntityById(commentId);
    }

    @Override
    public List<Comment> findComments(int pageNo, int pageSize, String condition, String param, Object o) {
        return getAllEntity(pageNo, pageSize, condition, param, o);
    }

    @Override
    public long getTotalComments(String param, Object o, String condition) {
        return getTotal(param, o, condition);
    }

    @Override
    public void updateComment(Comment comment) {
        update(comment);
    }

    @Override
    public void delete(Comment comment) {
        deleteEntity(comment);
    }
}

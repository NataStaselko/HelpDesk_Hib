package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.FeedbackDao;
import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.model.entity.Feedback;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackDaoImpl extends HibernateDao<Feedback> implements FeedbackDao{

    public FeedbackDaoImpl() {
        setModelClass(Feedback.class);
    }

    @Override
    public void save(Feedback feedback) {
        create(feedback);
    }

    @Override
    public List<Feedback> getFeedback(String param, Object o) {
        return getAllEntity(param, o);
    }
}

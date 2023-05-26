package com.final_project.staselko.dao;

import com.final_project.staselko.model.entity.Feedback;

import java.util.List;

public interface FeedbackDao {
    void save(Feedback feedback);
    List<Feedback> getFeedback(String param, Object value);
}

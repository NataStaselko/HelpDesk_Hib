package com.final_project.staselko.dao;

import com.final_project.staselko.model.entity.History;

import java.util.List;

public interface HistoryDao {
    void save(History history);
    List<History> findAllHistories(int pageNo, int pageSize, String condition,
                                   String param, Object o);
    long getTotalHistories(String param, Object o, String condition);
}

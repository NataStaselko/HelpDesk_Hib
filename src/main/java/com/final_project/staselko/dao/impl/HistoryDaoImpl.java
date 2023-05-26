package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.dao.HistoryDao;
import com.final_project.staselko.model.entity.History;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoryDaoImpl extends HibernateDao<History> implements HistoryDao {
    public HistoryDaoImpl() {
        setModelClass(History.class);
    }

    @Override
    public void save(History history) {
        create(history);
    }

    @Override
    public List<History> findAllHistories(int pageNo, int pageSize,
                                          String condition,
                                          String param, Object o) {
        return getAllEntity(pageNo, pageSize, condition, param, o);
    }

    @Override
    public long getTotalHistories(String param, Object o, String condition) {
        return getTotal(param, o, condition);
    }
}

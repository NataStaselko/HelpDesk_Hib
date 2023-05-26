package com.final_project.staselko.dao;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.*;

@RequiredArgsConstructor
public abstract class HibernateDao<T extends Serializable> {

    private Class<T> modelClass;
    @Autowired
    protected SessionFactory sessionFactory;

    public final void setModelClass(final Class<T> modelToSet) {
        this.modelClass = modelToSet;
    }

    public Optional<T> findEntityById(final long id) {
        Optional<T> entity;
        entity = ofNullable(getCurrentSession().get(modelClass, id));
        return entity;
    }

    public List<T> findAllEntities() {
        return getCurrentSession().createQuery("from " + modelClass.getName()).list();
    }

    public long create(final T entity) {
        return (long) getCurrentSession().save(entity);
    }

    public void update(final T entity) {
        getCurrentSession().merge(entity);
    }

    public void deleteEntity(final T entity) {
        getCurrentSession().delete(entity);
    }
    public T getEntity(String param, Object value){
        String q = "from " + modelClass.getName() + " where "
                + param + " = :param";
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param", value);
        return (T) query.getResultList().stream().findFirst().orElse(null);
    }

    public List<T> getAllEntity(String param, Object value){
        String q = "from " + modelClass.getName() + " where "
                + param + " = :param";
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param", value);
        return query.getResultList();
    }
    public List<T> getAllEntity(int pageNo, int pageSize, String condition,
                                String param, Object value){
        String q = "from " + modelClass.getName() + " where "
                +  "(" +param + " = :param) " + condition;
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param", value);
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public List<T> getAllEntity(int pageNo, int pageSize, String condition,
                                String param1, Object value1,
                                String param2, Object value2){
        String q = "from " + modelClass.getName() + " where "
                + "(" + param1 + " = :param1" + " or "
                + param2 + " = :param2) " + condition;
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param1", value1);
        query.setParameter("param2", value2);
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    public List<T> getAllEntity(int pageNo, int pageSize, String condition,
                                String param1, Object value1,
                                String param2, Object value2,
                                String param3, Object value3){
        String q = "from " + modelClass.getName() + " where "
                + "(" + param1 + " = :param1" + " or "
                + param2 + " = :param2" + " or "
                + param3 + " = :param3) " + condition;
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param1", value1);
        query.setParameter("param2", value2);
        query.setParameter("param3", value3);
        query.setFirstResult(pageNo * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    public long getTotal(String param, Object value, String condition){
        String q = "select count(id) from " + modelClass.getName() + " where "
                + param + " = :param " + condition;
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param", value);
        return (long) query.getSingleResult();
    }

    public long getTotal(String param1, Object value1,
                         String param2, Object value2,
                         String condition){
        String q = "select count(id) from " + modelClass.getName() + " where "
                + "(" + param1 + " = :param1" + " or "
                + param2 + " = :param2) " + condition;
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param1", value1);
        query.setParameter("param2", value2);
        return (long) query.getSingleResult();
    }

    public long getTotal(String param1, Object value1,
                         String param2, Object value2,
                         String param3, Object value3,
                         String condition){
        String q = "select count(id) from " + modelClass.getName() + " where "
                + "(" + param1 + " = :param1" + " or "
                + param2 + " = :param2" + " or "
                + param3 + " = :param3) " + condition;
        Query query = getCurrentSession().createQuery(q);
        query.setParameter("param1", value1);
        query.setParameter("param2", value2);
        query.setParameter("param3", value3);
        return (long) query.getSingleResult();
    }


    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}


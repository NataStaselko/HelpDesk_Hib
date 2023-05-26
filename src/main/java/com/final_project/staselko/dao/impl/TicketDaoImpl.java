package com.final_project.staselko.dao.impl;

import com.final_project.staselko.dao.HibernateDao;
import com.final_project.staselko.dao.TicketDao;
import com.final_project.staselko.model.entity.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketDaoImpl extends HibernateDao<Ticket> implements TicketDao {

    public TicketDaoImpl() {
        setModelClass(Ticket.class);
    }

    @Override
    public Long save(Ticket ticket) {
        return create(ticket);
    }

    @Override
    public Optional<Ticket> findById(Long ticketId) {
        return findEntityById(ticketId);
    }

    @Override
    public void updateTicket(Ticket ticket) {
        update(ticket);
    }

    @Override
    public List<Ticket> getTickets(int pageNo, int pageSize, String condition, String param, Object o) {
        return getAllEntity(pageNo, pageSize,condition, param, o);
    }

    @Override
    public List<Ticket> getTickets(int pageNo, int pageSize, String condition,
                                   String param1, Object o1, String param2, Object o2) {
        return getAllEntity(pageNo, pageSize, condition, param1,o1, param2, o2);
    }

    @Override
    public long getTotalTickets(String param, Object o, String condition) {
        return getTotal(param, o, condition);
    }

    @Override
    public long getTotalTickets(String param1, Object o1, String param2, Object o2, String condition) {
        return getTotal(param1, o1, param2, o2, condition);
    }

    @Override
    public List<Ticket> getTickets(int pageNo, int pageSize, String condition,
                                   String param1, Object o1,
                                   String param2, Object o2,
                                   String param3, Object o3) {
        return getAllEntity(pageNo, pageSize, condition, param1, o1, param2, o2, param3, o3);
    }

    @Override
    public long getTotalTickets(String param1, Object o1,
                                String param2, Object o2,
                                String param3, Object o3,
                                String condition) {
        return getTotal(param1, o1, param2, o2, param3, o3, condition);
    }
}

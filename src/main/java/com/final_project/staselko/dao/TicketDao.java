package com.final_project.staselko.dao;

import com.final_project.staselko.model.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketDao {
    Long save(Ticket ticket);

    Optional<Ticket> findById(Long ticketId);

    List<Ticket> getTickets(int pageNo, int pageSize,
                            String condition, String param,
                            Object o);

    List<Ticket> getTickets(int pageNo, int pageSize,
                            String condition, String param1,
                            Object o1, String param2,
                            Object o2);

    List<Ticket> getTickets(int pageNo, int pageSize,
                            String conditiony, String param1,
                            Object o1, String param2,
                            Object o2, String param3,
                            Object o3);

    long getTotalTickets(String param, Object o, String condition);
    long getTotalTickets(String param1, Object o1,
                         String param2, Object o2,
                         String condition);
    long getTotalTickets(String param1, Object o1,
                         String param2, Object o2,
                         String param3, Object o3,
                         String condition);

    void updateTicket(Ticket ticket);

}

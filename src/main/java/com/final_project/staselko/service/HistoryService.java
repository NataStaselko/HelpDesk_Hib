package com.final_project.staselko.service;

import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.response.HistoryResponse;

public interface HistoryService {
    void addHistory(Ticket ticket, User user, String action, String description);

    HistoryResponse getHistoriesByTicket(Long ticketId, int pageNo, int pageSize);
}

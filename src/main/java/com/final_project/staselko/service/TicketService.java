package com.final_project.staselko.service;

import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.enums.Action;
import com.final_project.staselko.model.response.TicketResponse;

import java.util.List;

public interface TicketService {

    Ticket createTicket(TicketDto ticketDto);
    Ticket getTicketById(Long ticketId);

    TicketDto getTicketDtoById(Long ticketDtoId);

    TicketResponse getTicketList(int pageNo, int pageSize, String sortBy, String orderBy,
                                 Long filter_id, String filter_name, String filter_date,
                                 String filter_urgency, String filter_state, String flag);

    void  updateOrChangeAction(Long ticketId, Action action, TicketDto ticketDto);
}

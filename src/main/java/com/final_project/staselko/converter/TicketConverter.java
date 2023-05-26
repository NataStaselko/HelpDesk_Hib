package com.final_project.staselko.converter;

import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;

public interface TicketConverter {
    Ticket toTicket(TicketDto ticketDto);
    TicketDto toTicketDto(Ticket ticket);
}
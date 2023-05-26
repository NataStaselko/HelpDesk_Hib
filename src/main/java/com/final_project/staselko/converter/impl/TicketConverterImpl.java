package com.final_project.staselko.converter.impl;

import com.final_project.staselko.converter.TicketConverter;
import com.final_project.staselko.converter.UserConverter;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.enums.Urgency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class TicketConverterImpl implements TicketConverter {
    private final UserConverter userConverter;


    @Override
    public Ticket toTicket(TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setId(ticketDto.getId());
        ticket.setName(ticketDto.getName());
        ticket.setDescription(ticketDto.getDescription());
        if (ticketDto.getDesired_date().equals("")) {
            ticketDto.setDesired_date(LocalDate.now()
                    .plusDays(Long.parseLong(String.valueOf(Urgency.valueOf(ticketDto.getUrgency()).getCode())) + 1)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        ticket.setDesired(ticketDto.getDesired_date());

        ticket.setCategory(ticketDto.getCategory());
        ticket.setUrgency(Urgency.valueOf(ticketDto.getUrgency()));
        ticket.setState(State.valueOf(ticketDto.getState()));
        ticket.setOwner(userConverter.toUser(ticketDto.getOwner()));
        return ticket;
    }

    @Override
    public TicketDto toTicketDto(Ticket ticket) {
        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setName(ticket.getName());
        ticketDto.setDesired_date(ticket.getDesired());
        ticketDto.setUrgency(ticket.getUrgency().name());
        ticketDto.setCreated_on(ticket.getCreated()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        ticketDto.setState(ticket.getState().name());
        ticketDto.setCategory(ticket.getCategory());
        ticketDto.setDescription(ticket.getDescription());
        ticketDto.setOwner(userConverter.toUserDto(ticket.getOwner()));
        if (ticket.getManager() != null) {
            ticketDto.setApproved(userConverter.toUserDto(ticket.getManager()));
        }
        if (ticket.getEngineer() != null){
            ticketDto.setAssignee(userConverter.toUserDto(ticket.getEngineer()));
        }
        return ticketDto;
    }
}

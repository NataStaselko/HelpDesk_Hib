package com.final_project.staselko.converter;

import com.final_project.staselko.converter.impl.TicketConverterImpl;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.dto.UserDto;
import com.final_project.staselko.model.entity.Category;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.enums.Urgency;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketConverterTest {
    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private TicketConverterImpl ticketConverter;

    @Test
    void convertTicket(){
        TicketDto ticketDto = getTicketDto();
        Ticket ticket = getTicket();
        User user = new User();
        UserDto userDto = new UserDto();
        when(userConverter.toUser(userDto)).thenReturn(user);
        Ticket test = ticketConverter.toTicket(ticketDto);
        assertEquals(ticket, test);
    }

    @Test
    void convertTicketDto(){
        TicketDto ticketDto = getTicketDto();
        ticketDto.setCreated_on(LocalDateTime.now().withNano(0)
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        Ticket ticket = getTicket();
        User user = new User();
        UserDto userDto = new UserDto();
        when(userConverter.toUserDto(user)).thenReturn(userDto);
        TicketDto test = ticketConverter.toTicketDto(ticket);
        assertEquals(ticketDto, test);
    }


    private TicketDto getTicketDto(){
        Category category = new Category();
        UserDto userDto = new UserDto();
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("name");
        ticketDto.setState("DRAFT");
        ticketDto.setUrgency("CRITICAL");
        ticketDto.setDesired_date("31/12/2023");
        ticketDto.setCategory(category);
        ticketDto.setOwner(userDto);
        return ticketDto;
    }

    private Ticket getTicket(){
        Category category = new Category();
        User user = new User();
        Ticket ticket = new Ticket();
        ticket.setName("name");
        ticket.setState(State.valueOf("DRAFT"));
        ticket.setUrgency(Urgency.valueOf("CRITICAL"));
        ticket.setCategory(category);
        ticket.setDesired("31/12/2023");
        ticket.setOwner(user);
        return ticket;
    }
}

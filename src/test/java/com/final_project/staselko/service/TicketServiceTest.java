package com.final_project.staselko.service;

import com.final_project.staselko.converter.TicketConverter;
import com.final_project.staselko.converter.UserConverter;
import com.final_project.staselko.dao.TicketDao;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.dto.UserDto;
import com.final_project.staselko.model.entity.Category;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.enums.Urgency;
import com.final_project.staselko.service.Impl.TicketServiceImpl;
import com.final_project.staselko.service.mail.MailService;
import com.final_project.staselko.utils.DescriptionHistoryCreator;
import com.final_project.staselko.utils.UserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @Mock
    private TicketConverter ticketConverter;

    @Mock
    private TicketDao ticketDao;

    @Mock
    private UserProvider userProvider;

    @Mock
    private UserConverter userConverter;

    @Mock
    private MailService mailService;

    @Mock
    private HistoryService historyService;

    @Mock
    private DescriptionHistoryCreator descriptionCreator;

    @InjectMocks
    private TicketServiceImpl ticketService;



    @Test
    void createTicket(){
        TicketDto ticketDto = getTicketDto();
        Ticket ticketBefore = getTicketBefore();
        Ticket ticketAfter = getTicketAfter();
        User user = new User();
        UserDto userDto = new UserDto();
        when(userProvider.getCurrentUser()).thenReturn(user);
        when(userConverter.toUserDto(user)).thenReturn(userDto);
        when(ticketConverter.toTicket(ticketDto)).thenReturn(ticketBefore);
        when(ticketDao.save(ticketBefore)).thenReturn(ticketAfter.getId());
        when(ticketDao.findById(1l)).thenReturn(Optional.of(ticketAfter));
        Ticket test = ticketService.createTicket(ticketDto);
        assertEquals(ticketAfter, test);
    }

    @Test
    void getTicketById(){
        Ticket ticket = getTicketAfter();
        when(ticketDao.findById(anyLong())).thenReturn(Optional.of(ticket));
        Ticket test = ticketService.getTicketById(1l);
        assertEquals(ticket, test);
    }

    private TicketDto getTicketDto(){
        Category category = new Category();
        TicketDto ticketDto = new TicketDto();
        ticketDto.setName("name");
        ticketDto.setState("DRAFT");
        ticketDto.setUrgency("CRITICAL");
        ticketDto.setCategory(category);
        return ticketDto;
    }

    private Ticket getTicketBefore(){
        Category category = new Category();
        Ticket ticket = new Ticket();
        ticket.setName("name");
        ticket.setState(State.valueOf("DRAFT"));
        ticket.setUrgency(Urgency.valueOf("CRITICAL"));
        ticket.setCategory(category);
        return ticket;
    }

    private Ticket getTicketAfter(){
        Category category = new Category();
        Ticket ticket = new Ticket();
        ticket.setId(1l);
        ticket.setName("name");
        ticket.setState(State.valueOf("DRAFT"));
        ticket.setUrgency(Urgency.valueOf("CRITICAL"));
        ticket.setCategory(category);
        return ticket;
    }


}

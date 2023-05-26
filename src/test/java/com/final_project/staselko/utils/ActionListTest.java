package com.final_project.staselko.utils;

import com.final_project.staselko.dao.FeedbackDao;
import com.final_project.staselko.dao.TicketDao;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Action;
import com.final_project.staselko.model.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ActionListTest {
    @Mock
    private FeedbackDao feedbackDao;

    @Mock
    private TicketDao ticketDao;

    @InjectMocks
    private ActionsList actionsList;

    @Test
    void getActionList() {
        User user = new User();
        user.setRole(Role.EMPLOYEE);
        Ticket ticket = new Ticket();
        ticket.setId(1l);

        TicketDto ticketDto = new TicketDto();
        ticketDto.setId(1l);
        ticketDto.setState("DRAFT");

        when(ticketDao.findById(1l)).thenReturn(Optional.of(ticket));
        List<String> test = actionsList.getActions(user, ticketDto);
        List<String> action = Arrays.asList(Action.SUBMIT.getCode(), Action.CANCEL.getCode());
        assertTrue(test.containsAll(action));
    }

}

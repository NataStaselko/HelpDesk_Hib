package com.final_project.staselko.utils;

import com.final_project.staselko.dao.FeedbackDao;
import com.final_project.staselko.dao.TicketDao;
import com.final_project.staselko.model.dto.TicketDto;
import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.entity.User;
import com.final_project.staselko.model.enums.Action;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ActionsList {
    private final FeedbackDao feedbackDao;
    private final TicketDao ticketDao;

    public List<String> getActions(User user, TicketDto ticketDto) {
        return mapState(ticketDto).get(getKey(user, ticketDto)).get(State.valueOf(ticketDto.getState()));
    }

    private Map<String, EnumMap<State, List<String>>> mapState(TicketDto ticketDto) {
        Ticket ticket = ticketDao.findById(ticketDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id " + ticketDto.getId()));

        EnumMap<State, List<String>> enumMapOwner = new EnumMap<>(State.class);
        enumMapOwner.put(State.DRAFT, Arrays.asList(Action.SUBMIT.getCode(), Action.CANCEL.getCode()));
        enumMapOwner.put(State.DECLINED, Arrays.asList(Action.SUBMIT.getCode(), Action.CANCEL.getCode()));

        EnumMap<State, List<String>> enumMapManager = new EnumMap<>(State.class);
        enumMapManager.put(State.NEW, Arrays.asList(Action.APPROVE.getCode(), Action.DECLINE.getCode(), Action.CANCEL.getCode()));

        EnumMap<State, List<String>> enumMapEngineer = new EnumMap<>(State.class);
        enumMapEngineer.put(State.APPROVED, Arrays.asList(Action.ASSIGN_TO_ME.getCode(), Action.CANCEL.getCode()));
        enumMapEngineer.put(State.IN_PROGRESS, Collections.singletonList(Action.DONE.getCode()));


        if (feedbackDao.getFeedback("ticket", ticket).isEmpty()) {
            enumMapOwner.put(State.DONE, Collections.singletonList(Action.LEAVE_FEEDBACK.getCode()));
        }else {
            enumMapOwner.put(State.DONE, Collections.singletonList(Action.VIEW_FEEDBACK.getCode()));
            enumMapEngineer.put(State.DONE, Collections.singletonList(Action.VIEW_FEEDBACK.getCode()));
        }

        return Map.of(
                "owner", enumMapOwner,
                "manager", enumMapManager,
                "engineer", enumMapEngineer
        );
    }

    private String getKey(User user, TicketDto ticketDto) {
        if (user.isEmployee()) {
            return "owner";
        } else if (user.isManager()) {
            if (ticketDto.getOwner().getId().equals(user.getId())) {
                return "owner";
            } else {
                return "manager";
            }
        }
        return "engineer";
    }
}


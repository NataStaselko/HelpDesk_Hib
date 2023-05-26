package com.final_project.staselko.utils;

import com.final_project.staselko.model.entity.Ticket;
import com.final_project.staselko.model.enums.Action;
import com.final_project.staselko.model.enums.State;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class StateByTicket {
    public State getNewState(Ticket ticket, Action action) {
        return enumMapState().get(ticket.getState()).get(action);
    }

    private EnumMap<State, EnumMap<Action, State>> enumMapState() {
        EnumMap<State, EnumMap<Action, State>> enumMap = new EnumMap<>(State.class);

        EnumMap<Action, State> toNew = new EnumMap<>(Action.class);
        toNew.put(Action.SUBMIT, State.NEW);
        toNew.put(Action.CANCEL, State.CANCELED);

        EnumMap<Action, State> toApprove = new EnumMap<>(Action.class);
        toApprove.put(Action.APPROVE, State.APPROVED);
        toApprove.put(Action.DECLINE, State.DECLINED);
        toApprove.put(Action.CANCEL, State.CANCELED);

        EnumMap<Action, State> toAssign = new EnumMap<>(Action.class);
        toAssign.put(Action.ASSIGN_TO_ME, State.IN_PROGRESS);
        toAssign.put(Action.CANCEL, State.CANCELED);

        EnumMap<Action, State> declined = new EnumMap<>(Action.class);
        declined.put(Action.SUBMIT, State.NEW);
        declined.put(Action.CANCEL, State.CANCELED);

        EnumMap<Action, State> toDone = new EnumMap<>(Action.class);
        toDone.put(Action.DONE, State.DONE);

        enumMap.put(State.DRAFT, toNew);
        enumMap.put(State.NEW, toApprove);
        enumMap.put(State.APPROVED, toAssign);
        enumMap.put(State.DECLINED, declined);
        enumMap.put(State.IN_PROGRESS, toDone);
        return enumMap;
    }
}

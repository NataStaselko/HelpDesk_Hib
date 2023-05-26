package com.final_project.staselko.utils.filter;

import com.final_project.staselko.model.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StateFilter {
    public State getState(String name) {
        if (name.toLowerCase().startsWith("a")) {
            return State.APPROVED;
        }
        if (name.toLowerCase().startsWith("i")) {
            return State.IN_PROGRESS;
        }
        if (name.toLowerCase().startsWith("c")) {
            return State.CANCELED;
        }
        if (name.toLowerCase().startsWith("n")) {
            return State.NEW;
        }
        if (name.length() == 1 && name.toLowerCase().startsWith("d")) {
            return State.DECLINED;
        }
        if (name.toLowerCase().startsWith("de")) {
            return State.DECLINED;
        }
        if (name.toLowerCase().startsWith("do")) {
            return State.DONE;
        }
        if (name.toLowerCase().startsWith("dr")) {
            return State.DRAFT;
        }
        return null;
    }
}

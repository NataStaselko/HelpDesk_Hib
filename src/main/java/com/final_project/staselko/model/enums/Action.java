package com.final_project.staselko.model.enums;


import java.util.stream.Stream;

public enum Action {
    SUBMIT ("Submit"),
    APPROVE ("Approve"),
    DECLINE ("Decline"),
    CANCEL ("Cancel"),
    ASSIGN_TO_ME ("Assign to Me"),
    DONE ("Done"),
    LEAVE_FEEDBACK ("Leave Feedback"),
    VIEW_FEEDBACK ("View Feedback");

    private final String code;

    private Action(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Action getAction(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(Action.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

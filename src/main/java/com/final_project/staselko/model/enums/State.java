package com.final_project.staselko.model.enums;

public enum State {
    DRAFT("draft"),
    NEW("new"),
    APPROVED("approved"),
    DECLINED("declined"),
    IN_PROGRESS("inPrpogress"),
    DONE("done"),
    CANCELED("canceled");

    private String code;

    private State(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}

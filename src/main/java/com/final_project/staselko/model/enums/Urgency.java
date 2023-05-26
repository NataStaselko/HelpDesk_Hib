package com.final_project.staselko.model.enums;

public enum Urgency {
    CRITICAL(0),
    HIGH(1),
    AVERAGE(2),
    LOW(3);

    private Integer code;

    private Urgency(Integer code) {
        this.code = code;
    }
    public Integer getCode() {
        return code;
    }
}

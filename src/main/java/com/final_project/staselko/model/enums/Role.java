package com.final_project.staselko.model.enums;

public enum Role {
    EMPLOYEE(0),
    MANAGER(1),
    ENGINEER(2);
    private Integer code;

    private Role(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

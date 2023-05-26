package com.final_project.staselko.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.final_project.staselko.model.enums.Role;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Users_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String first_name;

    @Column(name = "LAST_NAME")
    private String last_name;
    @JsonIgnore
    @Column(name = "ROLE_ID")
    private Role role;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Feedback> feedbacks = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "engineer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets_eng = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets_owner = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets_man = new ArrayList<>();

    public boolean isEmployee() {
        return getRole() == Role.EMPLOYEE;
    }

    public boolean isManager() {
        return getRole() == Role.MANAGER;
    }

    public boolean isEngineer() {
        return getRole() == Role.ENGINEER;
    }
}

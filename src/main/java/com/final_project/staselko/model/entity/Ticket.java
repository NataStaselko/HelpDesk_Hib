package com.final_project.staselko.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.final_project.staselko.model.enums.State;
import com.final_project.staselko.model.enums.Urgency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "TICKETS")
@NoArgsConstructor
public class Ticket implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Tickets_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 500)
    private String description;

    @Column(name = "CREATED_ON")
    private LocalDateTime created = LocalDateTime.now().withNano(0);

    @Column(name = "DESIRED_RESOLUTION_DATE")
    private String desired;

    @Column(name = "STATE_ID")
    private State state;

    @Column(name = "URGENCY_ID")
    private Urgency urgency;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;


    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNEE_ID")
    private User engineer;


    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "OWNER_ID")
    private User owner;


    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVER_ID")
    private User manager;

    @JsonIgnore
    @OneToOne(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Feedback feedbacks;

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<History> histories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Attachment> attachments = new ArrayList<>();


}

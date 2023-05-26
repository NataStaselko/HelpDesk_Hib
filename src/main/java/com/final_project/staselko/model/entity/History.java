package com.final_project.staselko.model.entity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "HISTORIES")
public class History implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Histories_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "DATE")
    private LocalDateTime date = LocalDateTime.now().withNano(0);

    @Column(name = "ACTION")
    private String action;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;
}

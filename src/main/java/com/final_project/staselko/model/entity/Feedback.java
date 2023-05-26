package com.final_project.staselko.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FEEDBACKS")
public class Feedback implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Feedbacks_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "RATE")
    private Integer rate;

    @Column(name = "Date")
    private LocalDateTime date = LocalDateTime.now().withNano(0);

    @Column(name = "TEXT", length = 500)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;

}

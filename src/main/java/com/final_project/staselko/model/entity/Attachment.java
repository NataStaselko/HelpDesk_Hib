package com.final_project.staselko.model.entity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "ATTACHMENTS")
public class Attachment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Attachments_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Lob
    @Column(name = "IMAGE", columnDefinition = "BLOB")
    private byte[] blob;

   // @FileTypeValid
    @Column(name = "NAME", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TICKET_ID")
    private Ticket ticket;
}

package com.final_project.staselko.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "CATEGORIES")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "Categories_id")
    @Column(name = "ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "NAME", length = 100)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

}


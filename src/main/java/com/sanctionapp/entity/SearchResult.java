package com.sanctionapp.entity;


import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "search_results")
public class SearchResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
    CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "person_id")
    private Person match;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "search_id")
    private SearchLogs search;

}

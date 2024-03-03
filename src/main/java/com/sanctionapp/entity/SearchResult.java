package com.sanctionapp.entity;


import com.sanctionapp.dto.Match;
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



    private String fullName;

    private Integer score;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "search_id")
    private SearchLogs search;

}

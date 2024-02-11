package com.sanctionapp.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "search_logs")
public class SearchLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;


    @CreationTimestamp
    @Column(name="search_time")
    private Date searchTime;

    @Column(name="searched_name")
    private String searchedName;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;

    @OneToMany(mappedBy = "search")
    private Set<SearchResult> matches = new HashSet<>();

    public void addMatch(SearchResult match) {
        if (match != null) {
            match.setSearch(this);
            this.matches.add(match);
        }
    }
}

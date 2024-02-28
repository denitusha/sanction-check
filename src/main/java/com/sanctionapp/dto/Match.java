package com.sanctionapp.dto;

import lombok.Data;

import java.util.List;

@Data
public class Match implements Comparable<Match>{

    private Long id;
    private String name;

    private Integer score;

    @Override
    public int compareTo(Match other) {
        // Compare matches based on their scores
        return Integer.compare(this.score, other.score);
    }
}

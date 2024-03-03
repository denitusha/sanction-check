package com.sanctionapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Match implements Comparable<Match>{

    private Long id;
    private String name;

    private Integer score;

    @Override
    public int compareTo(Match other) {
        return Integer.compare(this.score, other.score);
    }
}

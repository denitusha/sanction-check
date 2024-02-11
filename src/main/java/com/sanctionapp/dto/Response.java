package com.sanctionapp.dto;

import lombok.Data;


import java.util.HashSet;
import java.util.Set;

@Data
public class Response {

    private Set<Match> matches = new HashSet<>();

    public void addMatch(Match match) {
        matches.add(match);
    }
}

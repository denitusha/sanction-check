package com.sanctionapp.dto;

import lombok.Data;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class Response {

    private List<Match> matches = new ArrayList<>();

    public void addMatch(Match match) {
        matches.add(match);
    }
}

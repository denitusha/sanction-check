package com.sanctionapp.dto;

import lombok.Data;


import java.util.*;

@Data
public class Response {

    private List<Match> matches = new ArrayList<>();

    public void addMatch(Match match) {
        matches.add(match);
    }

    public void addAllMatches(PriorityQueue<Match> topMatches) {
        List<Match> tempMatches = new ArrayList<>();
        while (!topMatches.isEmpty()) {
            tempMatches.add(0, topMatches.poll()); // Add at the beginning to reverse order
        }
        matches.addAll(tempMatches);
    }




}

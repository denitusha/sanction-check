package com.sanctionapp.utils;

import com.sanctionapp.dto.Match;
import org.springframework.stereotype.Component;

import java.util.Comparator;


@Component
public class OurCustomComparator implements Comparator<Match>{
    public int compare(Match m1, Match m2){
        if (m1.getScore() > m2.getScore()) {
            return 1;
        } else if (m1.getScore() < m2.getScore()) {
            return -1;
        } else {
            return 0;
        }

    }
}

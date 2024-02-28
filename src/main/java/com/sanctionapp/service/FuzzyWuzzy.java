package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.dto.Match;
import com.sanctionapp.dto.Response;
import com.sanctionapp.entity.Person;
import lombok.RequiredArgsConstructor;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class FuzzyWuzzy {

    private final PersonRepository personRepository;
    private final Environment env;

    OurCustomComparator customComparator = new OurCustomComparator();


    public Response searchForSanctions(String fullName) {

        int maxMatches = env.getProperty("max-matches", Integer.class, 3);
        int threshold = env.getProperty("threshold", Integer.class, 85);


        Response response = new Response();

        PriorityQueue<Match> topMatches = new PriorityQueue<>(maxMatches, customComparator);
        List<Person> personList = personRepository.findAll();
        // Define the threshold for accepting a match

        // Iterate over each person
        for (Person person : personList) {
            // Calculate the fuzzy score
            int setRatio = FuzzySearch.tokenSetRatio(fullName, person.getFullName());

            // If the score is above the threshold, add it to the priority queue
            if (setRatio > threshold) {
                Match match = new Match();
                match.setId(person.getId());
                match.setName(person.getFullName());
                match.setScore(setRatio);

                // If the size of the priority queue exceeds the desired number of matches,
                // remove the lowest-scoring match
                if (topMatches.size() >= maxMatches) {
                    Match lowestMatch = topMatches.peek(); // Get the lowest-scoring match without removing it
                    if (match.getScore() > lowestMatch.getScore()) {
                        topMatches.poll(); // Remove the lowest-scoring match
                        topMatches.offer(match); // Add the new match
                    }
                } else {
                    topMatches.offer(match); // Add the match to topMatches if there's space
                }


            }
        }

        System.out.println(topMatches);
        // Add the top matches from the priority queue to the response
        response.addAllMatches(topMatches);

        return response;

    }
}

class OurCustomComparator implements Comparator<Match>{
    public int compare(Match m1, Match m2){
        //   We are returning the object in descending order of their age.
        if (m1.getScore() > m2.getScore()) {
            return 1; // Return -1 for descending order
        } else if (m1.getScore() < m2.getScore()) {
            return -1; // Return 1 for descending order
        } else {
            return 0; // Return 0 for equal scores
        }

    }
}

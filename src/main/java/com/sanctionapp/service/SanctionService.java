package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.dto.Match;
import com.sanctionapp.dto.Response;
import com.sanctionapp.entity.Person;
import com.sanctionapp.utils.OurCustomComparator;
import lombok.RequiredArgsConstructor;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class SanctionService {

    private final PersonRepository personRepository;
    private  final PersonCache personCache;
    private final Environment env;

    private final OurCustomComparator customComparator;


    public Response searchForSanctions(String fullName) {

        int maxMatches = env.getProperty("max-matches", Integer.class, 3);
        int threshold = env.getProperty("threshold", Integer.class, 85);


        Response response = new Response();

        PriorityQueue<Match> topMatches = new PriorityQueue<>(maxMatches, customComparator);
        List<Person> personList = personCache.getAllPersons();
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
                    if (match.getScore() > topMatches.peek().getScore()) {
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


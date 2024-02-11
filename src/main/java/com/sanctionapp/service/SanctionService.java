package com.sanctionapp.service;

import com.sanctionapp.dto.Match;
import com.sanctionapp.dto.Response;
import com.sanctionapp.entity.Person;
import lombok.RequiredArgsConstructor;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SanctionService {

    private final SearchLogsService searchLogsService;
    private final PersonService personService;



    public Response searchForSanctions(String fullName){
        String fullNameWithoutSpace = fullName.replaceAll("\\s", "");

        List<Long> ids = new ArrayList<>();
        List<Person> personList = searchForMatches(fullNameWithoutSpace);

        Response response = new Response();

        if(personList.isEmpty()){
            return response;
        }

        for(Person person: personList){

            Match match = new Match();

            Integer fuzzyRatio = FuzzySearch.ratio(fullNameWithoutSpace, person.getFullName());

            if(fuzzyRatio > 85){
                ids.add(person.getId());
                match.setName(person.getFullName());
                match.setScore(String.valueOf(fuzzyRatio));
                response.addMatch(match);
            }

        }

        searchLogsService.saveSearch(fullNameWithoutSpace, ids);

        return response;
    }


    public List<Person> searchForMatches(String fullName) {

        // Search in the logs
        List<Person> personList = searchLogsService.searchForLogs(fullName);

        // If no matches found in the logs, search in the database
        if (personList.isEmpty()) {
            personList = personService.searchByFullName(fullName);
            System.out.println("empty");
        }

        return personList;
    }




}

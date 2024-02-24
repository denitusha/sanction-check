package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonRepository;
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
public class FuzzyWuzzy {

    private final PersonRepository personRepository;

    public Response searchForSanctions(String fullName) {

        Response response = new Response();

        List<Person> personList = personRepository.findAll();


        for (Person person : personList) {
            Match match = new Match();


//            int partialRatio = 0; //FuzzySearch.partialRatio(fullName, person.getFullName());
//            int sortPartialRatio = 0 ;//FuzzySearch.tokenSortPartialRatio(fullName, person.getFullName());
//
//            int setPartialRatio = 0;//FuzzySearch.tokenSetPartialRatio(fullName, person.getFullName());
//
//            int sortRatio = 0;//FuzzySearch.tokenSortRatio(fullName, person.getFullName());

            int setRatio = FuzzySearch.tokenSetRatio(fullName, person.getFullName());


            int ratio = 85;

            if ( setRatio > ratio ) {

                match.setId(person.getId());
                match.setName(person.getFullName());
                match.setScore(setRatio);
                response.addMatch(match);
            }


        }

        return response;

    }
}
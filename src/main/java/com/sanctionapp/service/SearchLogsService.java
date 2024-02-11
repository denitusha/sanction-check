package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonDAO;
import com.sanctionapp.DAO.SearchLogsRepository;
import com.sanctionapp.DAO.SearchResultsRepository;
import com.sanctionapp.entity.Person;
import com.sanctionapp.entity.SearchLogs;
import com.sanctionapp.entity.SearchResult;
import com.sanctionapp.entity.Status;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchLogsService {


    private final SearchLogsRepository searchLogsRepository;
    private final SearchResultsRepository searchResultsRepository;
    private final PersonDAO personDAO;


    public List<Person> searchForLogs(String fullName) {


        List<Person> personList = new ArrayList<>();

        Optional<SearchLogs> searchLog = searchLogsRepository.findFirstBySearchedNameOrderBySearchedNameDesc(fullName);



        if (!searchLog.isEmpty()) {
            SearchLogs searchLog2 = searchLog.get();
            return getPersons(searchLog2);
        }

        // Perform additional processing if needed
        return personList;
    }

    private List<Person> getPersons( SearchLogs searchLog) {

        List<Person> personList = new ArrayList<>();

        List<SearchResult> searchResults = searchResultsRepository.findAllBySearchId(searchLog.getId());
        for(SearchResult searchResult: searchResults){
            Person person = searchResult.getMatch();
            personList.add(person);
        }

        return personList;
    }


    @Async
    protected void saveSearch(String fullName, List<Long> ids) {
        // Update the search logs asynchronously
        SearchLogs searchLog = new SearchLogs();
        searchLog.setSearchedName(fullName);

        if (ids.isEmpty()) {
            searchLog.setStatus(Status.Clear); // Assuming you have a Status enum
            searchLogsRepository.save(searchLog);
        } else {
            searchLog.setStatus(Status.Sanctioned); // Assuming you have a Status enum
            // Set the matches
            for (Long id : ids) {
                Person person = personDAO.findById(id);

                SearchResult searchResult = new SearchResult();
                searchResult.setMatch(person);
                searchResult.setSearch(searchLog);

                searchResultsRepository.save(searchResult);

//
            }
        }

    }
}

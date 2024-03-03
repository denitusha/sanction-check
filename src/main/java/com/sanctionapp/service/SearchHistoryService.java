package com.sanctionapp.service;

import com.sanctionapp.DAO.SearchLogsRepository;
import com.sanctionapp.DAO.SearchResultsRepository;
import com.sanctionapp.dto.Match;
import com.sanctionapp.entity.Person;
import com.sanctionapp.entity.SearchLogs;
import com.sanctionapp.entity.SearchResult;
import com.sanctionapp.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.PriorityQueue;

@Service
@RequiredArgsConstructor
public class SearchHistoryService {

    private final SearchLogsRepository searchLogsRepository;
    private final SearchResultsRepository searchResultsRepository;



    @Async
    protected void saveSearch(String fullName, PriorityQueue<Match> matches) {
        // Update the search logs asynchronously
        SearchLogs searchLog = new SearchLogs();
        searchLog.setSearchedName(fullName);


        if (matches.isEmpty()) {
            searchLog.setStatus(Status.Clear); // Assuming you have a Status enum
            searchLogsRepository.save(searchLog);
        } else {
            searchLog.setStatus(Status.Sanctioned); // Assuming you have a Status enum
            for (Match match : matches) {


                SearchResult searchResult = new SearchResult();
                searchResult.setScore(match.getScore());
                searchResult.setFullName(match.getName());
                searchResult.setSearch(searchLog);

                searchResultsRepository.save(searchResult);

//
            }
        }

    }
}


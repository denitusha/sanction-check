package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonDAO;
import com.sanctionapp.DAO.SearchLogsRepository;
import com.sanctionapp.DAO.SearchResultsRepository;
import com.sanctionapp.entity.Person;
import com.sanctionapp.entity.SearchLogs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.sanctionapp.dto.Response;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class SanctionService {

    private final PersonDAO personDAO;
    private final SearchLogsRepository searchLogsRepository;
    private final SearchResultsRepository searchResultsRepository;

    public List<Person> searchForSanctions(String fullName) {
        String fullNameWithoutSpace = fullName.replaceAll("\\s", "");


        Optional<SearchLogs> searchLog = searchLogsRepository.findFirstBySearchedNameOrderBySearchedNameDesc(fullNameWithoutSpace);



        if (!searchLog.isEmpty()) {
            SearchLogs searchLog2 = searchLog.get();
            return getPersons(searchLog2);
        }
        List<Person> matchedPersons = personDAO.searchByFullName(fullNameWithoutSpace);
        // Perform additional processing if needed
        return matchedPersons;
    }

    private Set<Person> getPersons( SearchLogs searchLogs) {
        Set<Person> personList = new HashSet<>();

        return responses;
    }
}

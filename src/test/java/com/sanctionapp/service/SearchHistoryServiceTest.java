package com.sanctionapp.service;


import com.sanctionapp.DAO.SearchLogsRepository;
import com.sanctionapp.DAO.SearchResultsRepository;
import com.sanctionapp.dto.Match;
import com.sanctionapp.entity.SearchLogs;
import com.sanctionapp.entity.SearchResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;


import java.util.PriorityQueue;



@ExtendWith(MockitoExtension.class)
public class SearchHistoryServiceTest {

    @Mock
    private SearchLogsRepository searchLogsRepository;

    @Mock
    private SearchResultsRepository searchResultsRepository;

    @InjectMocks
    private SearchHistoryService searchHistoryService;


    @Test
    void testSaveSearch_EmptyMatches() {
        String fullName = "John Doe";
        PriorityQueue<Match> matches = new PriorityQueue<>();

        searchHistoryService.saveSearch(fullName, matches);

        verify(searchLogsRepository).save(any(SearchLogs.class));
    }

    @Test
    void testSaveSearch_NonEmptyMatches() {
        String fullName = "John Doe";
        PriorityQueue<Match> matches = new PriorityQueue<>();
        matches.add(new Match(1L, "John Doe", 87));

        searchHistoryService.saveSearch(fullName, matches);

        verify(searchResultsRepository, times(matches.size())).save(any(SearchResult.class));
    }
}

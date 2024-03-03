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


import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
public class SanctionService {

    private  final PersonCache personCache;
    private final Environment env;

    private final OurCustomComparator customComparator;


    private final SearchHistoryService searchHistoryService;


    public Response searchForSanctions(String fullName) {


        int maxMatches = env.getProperty("max-matches", Integer.class, 3);

        Response response = new Response();

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        List<Future<List<Match>>> futures = new ArrayList<>();

        int numThreads = 2;
        Long batchSize = personCache.size() / numThreads;
        Long remaining = personCache.size() % numThreads;

        for (int i = 0; i < numThreads; i++) {
            Long start = i * batchSize;
            Long end = start + batchSize;
            if (i == numThreads - 1) {
                end += remaining;
            }

            futures.add(executorService.submit(new SearchTask(start, end, fullName)));
        }

        PriorityQueue<Match> topMatches = new PriorityQueue<>(maxMatches, customComparator);
        for (Future<List<Match>> future : futures) {
            try {
                List<Match> matches = future.get();
                for (Match match : matches) {
                   // System.out.println(match);
                    if (topMatches.size() < maxMatches || match.getScore() > topMatches.peek().getScore()) {
                        if (topMatches.size() >= maxMatches) {
                            topMatches.poll();
                        }
                        topMatches.offer(match);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        searchHistoryService.saveSearch(fullName, topMatches);
        response.addAllMatches(topMatches);
        return response;

    }

    private class SearchTask implements Callable<List<Match>> {

        int threshold = env.getProperty("threshold", Integer.class, 85);
        private final Long start;
        private final Long end;
        private final String fullName;

        public SearchTask(Long start, Long end, String fullName) {
            this.start = start;
            this.end = end;
            this.fullName = fullName;
        }

        @Override
        public List<Match> call() {
            List<Match> matches = new ArrayList<>();
            for (Long i = start; i < end; i++) {
                Person person = personCache.get(i);
                if (person != null) {
                    int setRatio = FuzzySearch.tokenSetRatio(fullName, person.getFullName());
                    if (setRatio >= threshold) {
                        Match match = Match.builder()
                                .id(person.getId())
                                .name(person.getFullName())
                                .score(setRatio)
                                .build();
                        matches.add(match);
                    }
                }
            }
            return matches;
        }

    }
}


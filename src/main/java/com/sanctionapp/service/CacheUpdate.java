package com.sanctionapp.service;


import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.entity.Person;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheUpdate {

    private final PersonCache personCache;
    private final PersonRepository personRepository;

    @Value("${cache.update.time}")
    private String cacheUpdateTime;

    @PostConstruct
    public void initializeCache() {
        String[] parts = cacheUpdateTime.split(":");
        String cronExpression = "0 " + parts[1] + " " + parts[0] + " * * *";

        System.out.println("Cache update scheduled at: " + cacheUpdateTime + " (cron expression: " + cronExpression + ")");

        updateCache();


    }

    @Scheduled(cron = "${cache.update.time.cron}")
    public Long updateCache() {
        personCache.clear();
        List<Person> personList = personRepository.findAll();

        System.out.println(personList.get(0));
        for(Person person: personList){

            personCache.put(person);
        }

        System.out.println(personCache.size());
        System.out.println("Cache is ready");
        return personCache.size();
    }


}

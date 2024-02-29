package com.sanctionapp.cache;

import com.sanctionapp.entity.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class PersonCache {

    private final Map<Long, Person> cache;

    public PersonCache() {
        // Initialize ConcurrentHashMap for thread-safe caching
        cache = new ConcurrentHashMap<>();
    }

    public void put(Person person) {
        cache.put(person.getId(), person);
    }

    public Person get(Long id) {
        return cache.get(id);
    }

    public void remove(Long id) {
        cache.remove(id);
    }

    public void clear() {
        cache.clear();
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(cache.values());
    }
}

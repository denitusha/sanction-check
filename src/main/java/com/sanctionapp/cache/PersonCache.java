package com.sanctionapp.cache;

import com.sanctionapp.entity.Person;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class PersonCache {

    private final Map<Long, Person> cache;

    private  Long size;

    public PersonCache() {
        // Initialize ConcurrentHashMap for thread-safe caching
        cache = new ConcurrentHashMap<>();
        size = 0L;
    }

    public void put(Person person) {
        cache.put(person.getId(), person);

        size++;
    }

    public Person get(Long id) {
        return cache.get(id);
    }

    public void remove(Long id) {
        Person person = cache.remove(id);
        if (person != null){
            size --;
        }
    }

    public void clear() {
        cache.clear();
        size = 0L;
    }

    public Long size() {
        return size;
    }

    public List<Person> getAllPersons() {
        return new ArrayList<>(cache.values());
    }


}

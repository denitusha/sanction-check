package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(MockitoExtension.class)
public class CacheServiceTest {


    @Mock
    private PersonCache personCache;

    @Mock
    private PersonRepository personRepository;

    @Value("${cache.update.time}")
    private String cacheUpdateTime;

    @InjectMocks
    private CacheUpdate cacheUpdate;



    @Test
    void testUpdateCacheEmpty() {
        // Mocking the behavior of PersonRepository.findAll()
        List<Person> mockPersonList;
        mockPersonList = Collections.emptyList();
        when(personRepository.findAll()).thenReturn(mockPersonList);

        // Calling the method to be tested
        cacheUpdate.updateCache();


        assertEquals(0, personCache.size());

    }

    @Test
    void testUpdateCache() {
        Person johnDoe = Person.builder()
                .id(1L)
                .fullName("John Doe")
                .firstName("John")
                .lastName("Doe")
                .gender("M")
                .country("USA")
                .build();

        Person janeSmith = Person.builder()
                .id(2L)
                .fullName("Jane Smith")
                .firstName("Jane")
                .lastName("Smith")
                .gender("F")
                .country("Canada")
                .build();

        // Mocking the behavior of PersonRepository.findAll()
        List<Person> personList = Arrays.asList(johnDoe, janeSmith);
        when(personRepository.findAll()).thenReturn(personList);


        cacheUpdate.updateCache();



        verify(personCache, org.mockito.Mockito.times(personList.size())).put(any(Person.class));

    }
}

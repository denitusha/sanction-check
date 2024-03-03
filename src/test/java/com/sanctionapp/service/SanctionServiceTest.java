package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.dto.Match;
import com.sanctionapp.dto.Response;
import com.sanctionapp.entity.Person;
import com.sanctionapp.utils.OurCustomComparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SanctionServiceTest {


    @Mock
    private PersonCache personCache;

    @Mock
    private Environment environment;

    @Mock
    private OurCustomComparator customComparator;
    @InjectMocks
    private SanctionService sanctionService;


    @Test
    void testSearchForSanctions() throws Exception {
        // Mock the behavior of Environment and PersonCache

        when(environment.getProperty("max-matches", Integer.class, 3)).thenReturn(3);
        when(environment.getProperty("threshold", Integer.class, 85)).thenReturn(85);
        when(personCache.size()).thenReturn(4L); // Mocking size as 4
        when(personCache.get(0L)).thenReturn(new Person(1L, "John Doe", "john", "doe", "m", "m"));
        when(personCache.get(1L)).thenReturn(new Person(2L, "Jsadaewn Doe", "john", "doe", "m", "m"));
        when(personCache.get(2L)).thenReturn(new Person(3L, "John Doe", "john", "doe", "m", "m"));
        when(personCache.get(3L)).thenReturn(new Person(4L, "John doe", "john", "doe", "m", "m"));


        // Call the method under test
        Response response = sanctionService.searchForSanctions("John Doe");

        System.out.println(response);
        // Assert the size of the response matches
        assertEquals(3, response.getMatches().size());
    }



}

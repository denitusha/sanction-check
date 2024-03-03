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

    @Mock
    private SearchHistoryService searchHistoryService;
    @InjectMocks
    private SanctionService sanctionService;


    @Test
    public void testSearchForSanctions1() {
        sanctionTest(3,
                Arrays.asList(
                        new Person(1L, "John Doe", "john", "doe", "m", "m"),
                        new Person(2L, "John Doe Smith", "john", "doe", "m", "m"),
                        new Person(3L, "Jon DO", "john", "doe", "m", "m")
                ),
                "John Doe",
                3);
    }
    @Test
    public void testSearchForSanctions2() {
        List<Person> personList = Arrays.asList(
                new Person(1L, "Bank of Kosova Kosova", "John", "Doe", "m", "Al"),
                new Person(2L, "Bank of Kosova", "John", "Doe", "m", "Al"),
                new Person(3L, "Bank of kongo", "John", "Doe", "m", "Al")
        );
        sanctionTest(3, personList, "Bank of Kosova", 2);

    }






    @Test
    public void testSearchForSanctions3() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );

        sanctionTest(1, personList, "Kieeda  Simjoni", 1);


    }

    @Test
    public void testSearchForSanctions4() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );

        sanctionTest(1, personList, "Keeida Simjoni", 1);

    }

    @Test
    public void testSearchForSanctions5() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );

        sanctionTest(1, personList, "Keeida asd Simjoni", 1);

    }


    @Test
    public void testSearchForSanctions6() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );

        sanctionTest(1, personList, "Keila Simani", 1);
    }

    @Test
    public void testSearchForSanctions7() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );

        sanctionTest(1, personList, "Kejda Simani", 1);

    }

    @Test
    public void testSearchForSanctions8() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );
        sanctionTest(1, personList, "Keeeejda Simoni", 1);

    }


    @Test
    public void testSearchForSanctions9() {

        List<Person> personList = Arrays.asList(
                new Person(1L, "Kejda Simoni", "John", "Doe", "m", "Al")

        );

        sanctionTest(1, personList, "Simoni kejda", 1);
    }

    @Test
    void testSearchForSanctions10() throws Exception {
        sanctionTest(4,
                Arrays.asList(
                        new Person(1L, "John Doe", "john", "doe", "m", "m"),
                        new Person(2L, "John Doe", "john", "doe", "m", "m"),
                        new Person(3L, "John Doe", "john", "doe", "m", "m"),
                        new Person(4L, "John doe", "john", "doe", "m", "m")
                ),
                "John Doe",
                3);
    }


    private void sanctionTest(int cacheSize, List<Person> persons, String searchNamed, int expectedSize) {
        when(environment.getProperty("max-matches", Integer.class, 3)).thenReturn(3);
        when(environment.getProperty("threshold", Integer.class, 85)).thenReturn(85);
        when(personCache.size()).thenReturn((long) cacheSize); // Mocking size as 4
        for (int i = 0; i < cacheSize; i++) {
            when(personCache.get((long) i)).thenReturn(persons.get(i));
        }

        Response response = sanctionService.searchForSanctions(searchNamed);

        assertEquals(expectedSize, response.getMatches().size());
    }

}






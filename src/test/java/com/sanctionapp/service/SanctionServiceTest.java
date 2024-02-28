package com.sanctionapp.service;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.dto.Response;
import com.sanctionapp.entity.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SanctionServiceTest {

    @Mock
    private  PersonRepository personRepository;
    @Mock
    private Environment environment;

    @InjectMocks
    private SanctionService sanctionService;

    @Test
    public void testSearchForSanctions() {
        // Mocking data for personRepository.findAll()
        List<Person> personList = Arrays.asList(
                new Person(1L, "John Doe", "John", "Doe", "m", "Al"),
                new Person(2L, "John Doe Smith", "John", "Doe", "m", "Al"),
                new Person(3L, "Jon DO", "John", "Doe", "m", "Al")
        );
        when(personRepository.findAll()).thenReturn(personList);

        // Mocking environment properties
        when(environment.getProperty("threshold", Integer.class, 85)).thenReturn(85);
        when(environment.getProperty("max-matches", Integer.class, 3)).thenReturn(3);



        // Performing the fuzzy search
        Response response = sanctionService.searchForSanctions("John doe");

        // Verifying the response
        assertEquals(3, response.getMatches().size());

    }
}

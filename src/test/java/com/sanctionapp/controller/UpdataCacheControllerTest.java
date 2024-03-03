package com.sanctionapp.controller;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.service.CacheUpdate;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(UpdateCacheController.class)
@ExtendWith(MockitoExtension.class)
public class UpdataCacheControllerTest {



    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private  CacheUpdate cacheUpdate;
    @MockBean
    private PersonCache personCache;

    @MockBean
    private PersonRepository personRepository;
    @Test
    public void testUpdateCache() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock the behavior of CacheUpdate
        when(cacheUpdate.updateCache()).thenReturn(5L);

        // Perform GET request to /api/update-cache
        mockMvc.perform(MockMvcRequestBuilders.get("/api/update-cache"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("5")); // Check response body
    }
}

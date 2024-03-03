package com.sanctionapp.controller;

import com.sanctionapp.DAO.PersonRepository;
import com.sanctionapp.cache.PersonCache;
import com.sanctionapp.dto.Match;
import com.sanctionapp.dto.Request;
import com.sanctionapp.dto.Response;
import com.sanctionapp.service.SanctionService;
import com.sanctionapp.service.SearchHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.PriorityQueue;

@WebMvcTest(controllers = SanctionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class SanctionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SanctionService sanctionService;


    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PersonRepository personRepository;
    @MockBean
    private PersonCache personCache;

    @MockBean
    private  SearchHistoryService searchHistoryService;

    @Test
    public void testPlaceOrderResponseEmpty() throws Exception {
        // Prepare test data
        Request request = new Request("John Doe");

        // Prepare mock response
        Response mockResponse = new Response();

        // Mock the behavior of SanctionService
        given(sanctionService.searchForSanctions(request.getFullName())).willReturn(mockResponse);

        ResultActions response = mockMvc.perform(post("/api/sanctions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request.getFullName()))) ;// Set the request body

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matches", hasSize(0))); // C Che

    }


    @Test
    public void testPlaceOrder() throws Exception {
        // Prepare test data
        Request request = new Request("John Doe");

        // Prepare mock response
        Response mockResponse = new Response();

        PriorityQueue<Match> matches = new PriorityQueue<>();
        matches.add(Match.builder()
                .id(1L)
                .name("John Doe")
                .score(100)
                .build());

        matches.add(Match.builder()
                .id(2L)
                .name("Johni Doe")
                .score(93)
                .build());
        mockResponse.addAllMatches(matches);

        // Mock the behavior of SanctionService
        given(sanctionService.searchForSanctions(request.getFullName())).willReturn(mockResponse);

        ResultActions response = mockMvc.perform(post("/api/sanctions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request.getFullName()))) ;// Set the request body

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.matches", hasSize(2))); // C Che

    }

}

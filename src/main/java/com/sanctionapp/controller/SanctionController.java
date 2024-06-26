package com.sanctionapp.controller;


import com.sanctionapp.dto.Request;
import com.sanctionapp.dto.Response;
import com.sanctionapp.service.SanctionService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SanctionController {

    private final SanctionService sanctionService;
    @PostMapping("/sanctions")
    public Response placeOrder(@RequestBody Request request) {

        Response response = sanctionService.searchForSanctions(request.getFullName());

        return response;
    }
}

package com.sanctionapp.controller;


import com.sanctionapp.dto.Request;
import com.sanctionapp.dto.Response;
import com.sanctionapp.service.CacheUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UpdateCacheController {

    private final CacheUpdate cacheUpdate;

    @GetMapping("/update-cache")
    public Long placeOrder() {


        Long size = cacheUpdate.updateCache();

        return size;
    }
}

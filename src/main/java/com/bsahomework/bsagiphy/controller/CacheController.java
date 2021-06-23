package com.bsahomework.bsagiphy.controller;

import com.bsahomework.bsagiphy.dto.CacheGenerateRequestDto;
import com.bsahomework.bsagiphy.dto.GifsDto;
import com.bsahomework.bsagiphy.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    CacheService cacheService;

    @GetMapping
    public List<GifsDto> searchGifs(@RequestParam(required = false) String query) {
        return cacheService.searchGifs(query);
    }

    @PostMapping("/generate")
    public List<GifsDto> generateGifs(@RequestBody CacheGenerateRequestDto body) {
        return cacheService.generateGifs(body.getQuery());
    }

    @DeleteMapping
    public void deleteCache() {
        cacheService.deleteCache();
    }

}

package com.bsahomework.bsagiphy.controller;

import com.bsahomework.bsagiphy.dto.GifsDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @GetMapping
    public List<GifsDto> searchGifs(@RequestParam(required = false) String query) {
        return null;
    }

    @GetMapping("/generate")
    public List<GifsDto> generateGifs(@RequestParam String query) {
        return null;
    }

    @DeleteMapping
    public void deleteCache() {

    }

}

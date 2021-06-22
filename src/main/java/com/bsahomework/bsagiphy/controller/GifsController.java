package com.bsahomework.bsagiphy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("gifs")
public class GifsController {

    @GetMapping
    public List<Path> getAllGifs() {
        return null;
    }

}

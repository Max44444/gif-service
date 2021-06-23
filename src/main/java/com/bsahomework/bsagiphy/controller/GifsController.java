package com.bsahomework.bsagiphy.controller;

import com.bsahomework.bsagiphy.service.GifsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gifs")
public class GifsController {

    @Autowired
    private GifsService gifsService;

    @GetMapping
    public List<String> getAllGifs() {
        return gifsService.getAllGifsPaths();
    }

}

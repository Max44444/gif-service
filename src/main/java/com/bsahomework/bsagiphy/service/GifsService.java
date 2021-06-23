package com.bsahomework.bsagiphy.service;

import com.bsahomework.bsagiphy.repository.GifsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GifsService {

    @Autowired
    private GifsRepository gifsRepository;

    public List<String> getAllGifsPaths() {
        return gifsRepository.getAllGifs().stream()
                .map(gif -> gif.getFile().getPath())
                .collect(Collectors.toList());
    }

}

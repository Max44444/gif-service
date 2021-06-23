package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GifsRepository {

    @Autowired
    FSRepository fsRepository;

    public List<Gif> getAllGifs() {
        var cache = fsRepository.getCacheDirectory();
        return fsRepository.getGifsFromFolder(cache, "");
    }

}

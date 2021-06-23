package com.bsahomework.bsagiphy.service;

import com.bsahomework.bsagiphy.dto.GifsDto;
import com.bsahomework.bsagiphy.exception.FileOperationsException;
import com.bsahomework.bsagiphy.mapper.GifMapper;
import com.bsahomework.bsagiphy.repository.CacheRepository;
import com.bsahomework.bsagiphy.util.GiphyApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CacheService {

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private GiphyApiClient apiClient;

    @Autowired
    private GifMapper gifMapper;

    public List<GifsDto> searchGifs(String query) {
        var checkedQuery = query == null ? "" : query.trim();
        var gifs = cacheRepository.getAllCache(checkedQuery);
        return gifMapper.gifToGifsDto(gifs);
    }

    public List<GifsDto> generateGifs(String query) {
        var checkedQuery = query == null ? "" : query.trim();
        apiClient.loadGifToFileByQuery(checkedQuery);
        return searchGifs(checkedQuery);
    }

    public void deleteCache() {
        try {
            cacheRepository.cleanCache();
        } catch (IOException e) {
            throw new FileOperationsException("Could not delete cache", e);
        }
    }

}

package com.bsahomework.bsagiphy.service;

import com.bsahomework.bsagiphy.dto.GifsDto;
import com.bsahomework.bsagiphy.dto.HistoryDto;
import com.bsahomework.bsagiphy.entity.Gif;
import com.bsahomework.bsagiphy.exception.FileOperationsException;
import com.bsahomework.bsagiphy.exception.NotFoundException;
import com.bsahomework.bsagiphy.mapper.GifMapper;
import com.bsahomework.bsagiphy.repository.CacheRepository;
import com.bsahomework.bsagiphy.repository.HistoryRepository;
import com.bsahomework.bsagiphy.repository.MemoryCacheRepository;
import com.bsahomework.bsagiphy.repository.UserRepository;
import com.bsahomework.bsagiphy.util.GiphyApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private MemoryCacheRepository memoryCacheRepository;

    @Autowired
    private GifMapper gifMapper;

    @Autowired
    private GiphyApiClient apiClient;


    public List<GifsDto> getAllGifsByUserId(String userId) {
        var gifs = userRepository.getAllGifsFromUserDirectory(userId);
        return gifMapper.gifToGifsDto(gifs);
    }

    public List<HistoryDto> getUserHistory(String userId) {
        try {
            return historyRepository.getUserHistory(userId);
        } catch (IOException e) {
            throw new NotFoundException("Could not read history for user: " + userId, e);
        }
    }

    public void cleanUserHistory(String userId) {
        try {
            historyRepository.cleanUserHistory(userId);
        } catch (IOException e) {
            throw new FileOperationsException("Could not clean history for user: " + userId, e);
        }
    }

    public String searchImage(String userId, String query, Boolean force) {
        Optional<Gif> gifOpt = Optional.empty();
        if (!Boolean.TRUE.equals(force)) {
            gifOpt = memoryCacheRepository.findUserGifByQuery(userId, query);
        }
        if (gifOpt.isEmpty()) {
            var gif = userRepository.findUserGifByQuery(userId, query).orElseThrow();
            memoryCacheRepository.addGifToUserCache(gif, userId);
            return searchImage(userId, query, false);
        }
        return gifOpt.get().getFile().getPath();
    }

    public String generateGif(String userId, String query, Boolean force) {
        try {
            Optional<Gif> gifOpt = Optional.empty();
            if (!Boolean.TRUE.equals(force)) {
                gifOpt = cacheRepository.findGifByQuery(query);
            }
            if (gifOpt.isEmpty()) {
                apiClient.loadGifToFileByQuery(query);
                return generateGif(userId, query, false);
            }
            var gif = gifOpt.get();
            userRepository.addGifToUserDirectory(gif, userId);
            memoryCacheRepository.addGifToUserCache(gif, userId);
            historyRepository.addRecordToUserHistory(gif, userId);
            return gif.getFile().getPath();
        } catch (IOException e) {
            throw new FileOperationsException("Could not generate gif for query: " + query, e);
        }
    }

    public void resetCache(String userId, String query) {
        var checkedQuery = query == null ? "" : query.trim();
        memoryCacheRepository.resetUserCacheByQuery(userId, checkedQuery);
    }

    public void cleanUserData(String userId) {
        try {
            memoryCacheRepository.resetUserCacheByQuery(userId, "");
            userRepository.cleanUserData(userId);
        } catch (IOException e) {
            throw new FileOperationsException("Could not clean user data for user: " + userId, e);
        }
    }

}

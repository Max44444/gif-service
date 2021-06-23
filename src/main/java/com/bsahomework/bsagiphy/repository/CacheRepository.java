package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class CacheRepository {

    @Autowired
    FSRepository fsRepository;

    public Optional<Gif> findGifByQuery(@NonNull String query) {
        var queryDirectory = new File(fsRepository.getCacheDirectory(), query);
        var file = fsRepository.getInnerFiles(queryDirectory).stream().findAny();
        return Gif.fileToGif(file);
    }

    public List<Gif> getAllCache(@NonNull String query) {
        var cache = fsRepository.getCacheDirectory();
        return fsRepository.getGifsFromFolder(cache, query);
    }

    public void cleanCache() throws IOException {
        var cache = fsRepository.getCacheDirectory();
        FileUtils.cleanDirectory(cache);
    }

}

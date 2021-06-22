package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class CacheRepository {

    @Autowired
    FSRepository fsRepository;

    public Optional<Gif> findGifByQuery(@NonNull String query) {
        var cache = fsRepository.getCacheDirectory();
        var queryDirectoryOpt = fsRepository.findFileInFolder(cache, query);

        if (queryDirectoryOpt.isEmpty()) {
            return Optional.empty();
        }

        var queryDirectory = queryDirectoryOpt.get();
        var gifsOpt = Optional.ofNullable(queryDirectory.listFiles()).orElse(new File[]{});
        var file = Arrays.stream(gifsOpt).findAny();

        if (file.isEmpty()) {
            return Optional.empty();
        }

        var gif = new Gif(file.get(), Optional.empty(), query);
        return Optional.of(gif);
    }

    public void addGifToCache(@NonNull Gif gif, @NonNull String query) throws IOException {
        var cache = fsRepository.getCacheDirectory();
        var queryDirectory = fsRepository.getIfPresentInFolderOrCreate(cache, query);
        FileUtils.copyFileToDirectory(gif.getFile(), queryDirectory);
    }


}

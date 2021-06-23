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
public class UserRepository {

    @Autowired
    private FSRepository fsRepository;

    public List<Gif> getAllGifsFromUserDirectory(@NonNull String userId) {
        var userDirectory = new File(fsRepository.getUsersDirectory(), userId);
        return fsRepository.getGifsFromFolder(userDirectory, "");
    }

    public Optional<Gif> findUserGifByQuery(@NonNull String userId, @NonNull String query) {
        var queryDirectory = new File(fsRepository.getUsersDirectory(), userId + "/" + query);
        var file = fsRepository.getInnerFiles(queryDirectory).stream().findAny();
        return Gif.fileToGif(file);
    }

    public void addGifToUserDirectory(@NonNull Gif gif, @NonNull String userId) throws IOException {
        var users = fsRepository.getUsersDirectory();
        var userDirectory = fsRepository.getIfPresentInFolderOrCreate(users, userId);
        var gifDirectory = fsRepository.getIfPresentInFolderOrCreate(userDirectory, gif.getQuery());
        FileUtils.copyFileToDirectory(gif.getFile(), gifDirectory);
    }

    public void resetUserCacheByQuery(@NonNull String userId, @NonNull String query) throws IOException {
        var queryDirectory = new File(fsRepository.getUsersDirectory(), userId + "/" + query);
        FileUtils.cleanDirectory(queryDirectory);
    }

    public void cleanUserData(@NonNull String userId) throws IOException {
        var userDirectory = new File(fsRepository.getUsersDirectory(), userId);
        FileUtils.deleteDirectory(userDirectory);
    }

}

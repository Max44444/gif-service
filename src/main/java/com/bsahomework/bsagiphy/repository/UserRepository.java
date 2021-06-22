package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserRepository {

    @Autowired
    private FSRepository fsRepository;

    public void addGifToUserDirectory(@NonNull Gif gif, @NonNull String userId) throws IOException {
        var users = fsRepository.getUsersDirectory();
        var userDirectory = fsRepository.getIfPresentInFolderOrCreate(users, userId);
        var gifDirectory = fsRepository.getIfPresentInFolderOrCreate(userDirectory, gif.getQuery());
        FileUtils.copyFileToDirectory(gif.getFile(), gifDirectory);
    }

}

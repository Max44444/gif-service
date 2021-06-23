package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class FSRepository {

    @Value("${fs-db.cache-directory}")
    private String cacheDirectory;

    @Value("${fs-db.users-directory}")
    private String usersDirectory;

    public File getCacheDirectory() {
        return new File(cacheDirectory);
    }

    public File getUsersDirectory() {
        return new File(usersDirectory);
    }

    public Optional<File> findFileInFolder(@NonNull File folder, @NonNull String filename) {
        return Arrays.stream(folder.listFiles(name -> name.equals(filename))).findFirst();
    }

    public File getIfPresentInFolderOrCreate(@NonNull File folder, @NonNull String filename) throws IOException {
        var file = findFileInFolder(folder, filename);

        if (file.isPresent()) {
            return file.get();
        }

        var newFile = new File(folder, filename);
        FileUtils.touch(newFile);
        return newFile;
    }

    public List<File> getInnerFiles(@NonNull File file) {
        var innerFiles = file.listFiles();

        if (innerFiles == null) {
            innerFiles = new File[0];
        }

        return Arrays.asList(innerFiles);
    }

    public List<Gif> getGifsFromFolder(@NonNull File folder, @NonNull String query) {
        return getInnerFiles(folder).stream()
                .filter(file -> query.isBlank() || file.getName().equals(query))
                .flatMap(file -> {
                    var gifs = getInnerFiles(file);
                    return gifs.stream()
                            .filter(gif -> FilenameUtils.getExtension(gif.getName()).equals("gif"))
                            .map(gif -> new Gif(gif, file.getName()));
                }).toList();
    }

}

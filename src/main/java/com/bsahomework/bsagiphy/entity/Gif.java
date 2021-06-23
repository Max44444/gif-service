package com.bsahomework.bsagiphy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.util.Optional;

@Data
@AllArgsConstructor
public class Gif {

    private File file;
    private Optional<String> userId;
    private String query;

    public Gif(File file, String query) {
        this(file, Optional.empty(), query);
    }

    public static Optional<Gif> fileToGif(Optional<File> file) {
        if (file.isEmpty()) {
            return Optional.empty();
        }

        var gifFile = file.get();
        var gif = new Gif(gifFile, gifFile.getParent());
        return Optional.of(gif);
    }

}

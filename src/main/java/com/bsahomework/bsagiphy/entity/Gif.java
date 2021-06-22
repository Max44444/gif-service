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

}

package com.bsahomework.bsagiphy.entity;

import lombok.Data;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Data
public class User {

    private String id;
    private Map<String, List<Path>> gifs;

}

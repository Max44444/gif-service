package com.bsahomework.bsagiphy.dto;

import lombok.Data;

import java.nio.file.Path;
import java.util.List;

@Data
public class GifsDto {

    private String query;
    private List<Path> gifs;

}

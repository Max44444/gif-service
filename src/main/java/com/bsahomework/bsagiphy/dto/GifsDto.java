package com.bsahomework.bsagiphy.dto;

import lombok.Data;

import java.util.List;

@Data
public class GifsDto {

    private String query;
    private List<String> paths;

}

package com.bsahomework.bsagiphy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GifsDto {

    private String query;
    private List<String> paths;

}

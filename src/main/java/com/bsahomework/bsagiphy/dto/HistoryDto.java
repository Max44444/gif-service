package com.bsahomework.bsagiphy.dto;

import lombok.Data;

import java.nio.file.Path;
import java.util.Date;

@Data
public class HistoryDto {

    private Date date;
    private String query;
    private Path gif;

}

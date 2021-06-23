package com.bsahomework.bsagiphy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class User {

    private String id;
    private List<Gif> gifs;

}

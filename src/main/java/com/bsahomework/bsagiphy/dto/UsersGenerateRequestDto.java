package com.bsahomework.bsagiphy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersGenerateRequestDto {

    private String query;
    private Boolean force;

}

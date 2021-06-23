package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.dto.HistoryDto;
import com.bsahomework.bsagiphy.entity.Gif;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryRepository {

    @Autowired
    FSRepository fsRepository;

    public void addGenerationRecordToUserHistory(@NonNull Gif gif) {

    }

    public List<HistoryDto> getUserHistory(@NonNull String userId) {
        return null;
    }

    public void cleanUserHistory(@NonNull String userId) {

    }

}

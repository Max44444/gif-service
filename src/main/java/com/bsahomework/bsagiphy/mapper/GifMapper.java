package com.bsahomework.bsagiphy.mapper;

import com.bsahomework.bsagiphy.dto.GifsDto;
import com.bsahomework.bsagiphy.entity.Gif;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GifMapper {

    public List<GifsDto> gifToGifsDto(List<Gif> gifs) {
        return groupGifsByQuery(gifs)
                .entrySet().stream()
                .map(this::entryToGifsDto)
                .collect(Collectors.toList());
    }

    private Map<String, List<Gif>> groupGifsByQuery(List<Gif> gifs) {
        return gifs.stream().collect(Collectors.groupingBy(Gif::getQuery));
    }

    private GifsDto entryToGifsDto(Map.Entry<String, List<Gif>> entry) {
        var query = entry.getKey();
        var gifs = entry.getValue();

        var paths = gifs.stream()
                .map(gif -> gif.getFile().getPath())
                .collect(Collectors.toList());

        return new GifsDto(query, paths);
    }

}

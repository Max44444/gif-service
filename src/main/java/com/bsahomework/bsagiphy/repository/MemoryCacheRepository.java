package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import com.bsahomework.bsagiphy.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MemoryCacheRepository {

    private final List<User> cacheData = new ArrayList<>();

    public void addGifToUserCache(@NonNull String userId, @NonNull Gif gif) {
        var userOpt = findUserInCache(userId);

        if (userOpt.isPresent()) {
            userOpt.get().getGifs().add(gif);
        } else {
            var newUser = new User(userId, Arrays.asList(gif));
            cacheData.add(newUser);
        }
    }

    public List<Gif> getUserGifByQuery(@NonNull String userId, @NonNull String query) {
        var userOpt = findUserInCache(userId);

        if (userOpt.isEmpty()) {
            return List.of();
        }

        var user = userOpt.get();
        return user.getGifs().stream()
                .filter(gif -> gif.getQuery().equals(query))
                .toList();
    }

    public void resetUserCacheByQuery(@NonNull String userId, @NonNull String query) {
        var userOpt = findUserInCache(userId);
        userOpt.ifPresent(user -> {
            if (query.isBlank()) {
                cacheData.remove(user);
            } else {
                user.getGifs().removeIf(gif -> gif.getQuery().equals(query));
            }
        });
    }

    private Optional<User> findUserInCache(@NonNull String userId) {
        return cacheData.stream()
                .filter(user -> user.getId().equals(userId))
                .findAny();
    }

}

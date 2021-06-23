package com.bsahomework.bsagiphy.repository;

import com.bsahomework.bsagiphy.entity.Gif;
import com.bsahomework.bsagiphy.entity.User;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class MemoryCacheRepository {

    private final List<User> cacheData = new ArrayList<>();

    public void addGifToUserCache(@NonNull Gif gif, @NonNull String userId) {
        var userOpt = findUserInCache(userId);

        if (userOpt.isPresent()) {
            userOpt.get().getGifs().add(gif);
        } else {
            var newUser = new User(userId, new ArrayList<Gif>(){{add(gif);}});
            cacheData.add(newUser);
        }
    }

    public Optional<Gif> findUserGifByQuery(@NonNull String userId, @NonNull String query) {
        var userOpt = findUserInCache(userId);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        var user = userOpt.get();
        return user.getGifs().stream()
                .filter(gif -> gif.getQuery().equals(query))
                .findAny();
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

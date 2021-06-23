package com.bsahomework.bsagiphy.controller;

import com.bsahomework.bsagiphy.dto.GifsDto;
import com.bsahomework.bsagiphy.dto.HistoryDto;
import com.bsahomework.bsagiphy.dto.UsersGenerateRequestDto;
import com.bsahomework.bsagiphy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}/all")
    public List<GifsDto> getAllGifsByUserId(@PathVariable String userId) {
        return userService.getAllGifsByUserId(userId);
    }

    @GetMapping("/{userId}/history")
    public List<HistoryDto> getUserHistory(@PathVariable String userId) {
        return userService.getUserHistory(userId);
    }

    @DeleteMapping("/{userId}/history/clean")
    public void cleanUserHistory(@PathVariable String userId) {
        userService.cleanUserHistory(userId);
    }

    @GetMapping("/{userId}/search")
    public String searchImage(
            @PathVariable String userId,
            @RequestParam String query,
            @RequestParam(required = false) Boolean force
    ) {
        return userService.searchImage(userId, query, force);
    }

    @PostMapping("/{userId}/generate")
    public String generateImage(@PathVariable String userId, @RequestBody UsersGenerateRequestDto body) {
        return userService.generateGif(userId, body.getQuery(), body.getForce());
    }

    @DeleteMapping("/{userId}/reset")
    public void resetUserCache(@PathVariable String userId, @RequestParam(required = false) String query) {
        userService.resetCache(userId, query);
    }

    @DeleteMapping("/{userId}/clean")
    public void cleanUserData(@PathVariable String userId) {
        userService.cleanUserData(userId);
    }

}

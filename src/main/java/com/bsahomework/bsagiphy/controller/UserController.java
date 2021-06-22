package com.bsahomework.bsagiphy.controller;

import com.bsahomework.bsagiphy.dto.GifsDto;
import com.bsahomework.bsagiphy.dto.HistoryDto;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @GetMapping("/:userId/all")
    public List<GifsDto> getAllGifsByUserId(@PathVariable String userId) {
        return null;
    }

    @GetMapping("/:userId/history")
    public List<HistoryDto> getUserHistory(@PathVariable String userId) {
        return null;
    }

    @DeleteMapping("/:userId/history/clean")
    public void cleanUserHistory(@PathVariable String userId) {

    }

    @GetMapping("/:userId/search")
    public Path searchImage(
            @PathVariable String userId,
            @RequestParam String query,
            @RequestParam(required = false) Boolean force
    ) {
        return null;
    }

    @GetMapping("/:userId/generate")
    public Path generateImage(
            @PathVariable String userId,
            @RequestParam String query,
            @RequestParam(required = false) Boolean force
    ) {
        return null;
    }

    @DeleteMapping("/:userId/reset")
    public void resetUserCache(@RequestParam(required = false) String query) {

    }

    @DeleteMapping("/:userId/clean")
    public void cleanUserData() {

    }

}

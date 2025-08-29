package lab.redisprac.controller;

import lab.redisprac.service.LeaderBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LeaderBoardController {
    private final LeaderBoardService leaderboardService;

    @PostMapping("/api/leaderboard/scores")
    public ResponseEntity<?> addScore(
            @RequestParam String userId,
            @RequestParam double score) {
        leaderboardService.addScore(userId, score);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/leaderboard/top/{count}")
    public ResponseEntity<List<?>> getTopPlayers(@PathVariable int count) {
        return ResponseEntity.ok(leaderboardService.getTopPlayers(count));
    }

    @GetMapping("/api/leaderboard/rank/{userId}")
    public ResponseEntity<Long> getUserRank(@PathVariable String userId) {
        Long rank = leaderboardService.getUserRank(userId);
        return rank != null ? ResponseEntity.ok(rank + 1) : ResponseEntity.notFound().build();
    }
}
package lab.redisprac.service;

import lab.redisprac.config.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderBoardService {

    private final RedisUtil redisUtil;
    private static final String LEADERBOARD_KEY = "game:leaderboard";

    public void addScore(String userId, double score) {
        redisUtil.addZSetScore(LEADERBOARD_KEY, userId, score);
        log.info("Score added for user: {} with score: {}", userId, score);
    }

    public List<?> getTopPlayers(int count) {
        Set<?> topScores = redisUtil.getZSetTop(LEADERBOARD_KEY, 0, count - 1);
        return new ArrayList<>(topScores != null ? topScores : Collections.emptySet());
    }

    public Long getUserRank(String userId) {
        return redisUtil.getZSetRank(LEADERBOARD_KEY, userId);
    }

    public Double getUserScore(String userId) {
        return redisUtil.getZSetScore(LEADERBOARD_KEY, userId);
    }
}
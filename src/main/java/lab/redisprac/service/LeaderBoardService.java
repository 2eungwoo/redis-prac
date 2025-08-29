package lab.redisprac.service;

import lab.redisprac.dto.RankingInfo;
import lab.redisprac.exception.LeaderBoardOperationException;
import lab.redisprac.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LeaderBoardService {

    private final LeaderBoardRepository leaderBoardRepository;
    private static final String LEADERBOARD_KEY = "game:leaderboard";

    public void addScore(String userId, double score) {
        try {
            leaderBoardRepository.addZSetScore(LEADERBOARD_KEY, userId, score);
            log.info("Score added for user: {} with score: {}", userId, score);
        } catch (Exception e) {
            throw new LeaderBoardOperationException("Failed to add score for user: " + userId, e);
        }
    }

    public List<RankingInfo> getTopPlayers(int count) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> topScores = leaderBoardRepository.getZSetTopWithScores(LEADERBOARD_KEY, 0, count - 1);
            if (topScores == null) {
                return Collections.emptyList();
            }
            return topScores.stream()
                .map(tuple -> new RankingInfo(String.valueOf(tuple.getValue()), tuple.getScore()))
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LeaderBoardOperationException("Failed to get top players", e);
        }
    }

    public Long getUserRank(String userId) {
        try {
            return leaderBoardRepository.getZSetRank(LEADERBOARD_KEY, userId);
        } catch (Exception e) {
            throw new LeaderBoardOperationException("Failed to get rank for user: " + userId, e);
        }
    }

    public Double getUserScore(String userId) {
        try {
            return leaderBoardRepository.getZSetScore(LEADERBOARD_KEY, userId);
        } catch (Exception e) {
            throw new LeaderBoardOperationException("Failed to get score for user: " + userId, e);
        }
    }
}

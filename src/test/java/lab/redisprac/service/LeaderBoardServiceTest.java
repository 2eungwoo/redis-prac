package lab.redisprac.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeaderBoardServiceTest {
    @Autowired
    private LeaderBoardService leaderboardService;

    @Test
    void leaderboardOperationsTest() {
        // given
        String userId = "user5"; // top ranker
        double score = 500.0;

        // when
        leaderboardService.addScore(userId, score);
        List<?> topPlayers = leaderboardService.getTopPlayers(1);
        Long rank = leaderboardService.getUserRank(userId);

        // then
        assertFalse(topPlayers.isEmpty());
        assertEquals(userId, topPlayers.get(0));
        assertEquals(0L, rank); // 첫 번째 순위 (0-based index)
    }
}
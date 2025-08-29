package lab.redisprac.config.redis;

import lab.redisprac.repository.LeaderBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisZSetUtil implements LeaderBoardRepository {

    private final ZSetOperations<String, Object> zSetOperations;

    public void addZSetScore(String key, Object member, double score) {
        zSetOperations.add(key, member, score);
    }

    public Set<Object> getZSetTop(String key, long start, long end) {
        return zSetOperations.reverseRange(key, start, end);
    }

    public Long getZSetRank(String key, Object member) {
        return zSetOperations.reverseRank(key, member);
    }

    public Double getZSetScore(String key, Object member) {
        return zSetOperations.score(key, member);
    }

    public Set<ZSetOperations.TypedTuple<Object>> getZSetTopWithScores(String key, long start, long end) {
        return zSetOperations.reverseRangeWithScores(key, start, end);
    }
}
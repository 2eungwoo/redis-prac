package lab.redisprac.repository;

import java.util.Set;
import lab.redisprac.config.redis.RedisZSetUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LeaderBoardRepository {

    private final RedisZSetUtil redisZSetUtil;

    public void addZSetScore(String key, Object member, double score) {
        redisZSetUtil.addZSetScore(key, member, score);
    }

    public Set<Object> getZSetTop(String key, long start, long end) {
        return redisZSetUtil.getZSetTop(key, start, end);
    }

    public Long getZSetRank(String key, Object member) {
        return redisZSetUtil.getZSetRank(key, member);
    }

    public Double getZSetScore(String key, Object member) {
        return redisZSetUtil.getZSetScore(key, member);
    }
}
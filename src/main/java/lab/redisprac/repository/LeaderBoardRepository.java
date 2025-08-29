package lab.redisprac.repository;

import java.util.Set;
import org.springframework.data.redis.core.ZSetOperations;

public interface LeaderBoardRepository {

    void addZSetScore(String key, Object member, double score);

    Set<Object> getZSetTop(String key, long start, long end);

    Set<ZSetOperations.TypedTuple<Object>> getZSetTopWithScores(String key, long start, long end);

    Long getZSetRank(String key, Object member);

    Double getZSetScore(String key, Object member);
}
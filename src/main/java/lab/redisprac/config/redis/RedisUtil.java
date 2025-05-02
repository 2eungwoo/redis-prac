package lab.redisprac.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public Object getData(String key) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public void setData(String key, Object value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataExpire(String key, Object value, long durationSeconds) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, Duration.ofSeconds(durationSeconds));
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }


    // zset
    public void addZSetScore(String key, String member, double score) {
        redisTemplate.opsForZSet().add(key, member, score);
    }

    public Set<Object> getZSetTop(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Long getZSetRank(String key, String member) {
        return redisTemplate.opsForZSet().reverseRank(key, member);
    }

    public Double getZSetScore(String key, String member) {
        return redisTemplate.opsForZSet().score(key, member);
    }
}
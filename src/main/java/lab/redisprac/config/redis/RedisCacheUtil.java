package lab.redisprac.config.redis;

import java.util.concurrent.TimeUnit;
import lab.redisprac.repository.CacheRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisCacheUtil implements CacheRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    public void setDataExpire(String key, Object value, long duration) {
        redisTemplate.opsForValue().set(key, value, duration, TimeUnit.SECONDS);
    }

    public Object getData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
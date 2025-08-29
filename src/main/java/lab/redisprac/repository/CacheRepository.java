package lab.redisprac.repository;

import lab.redisprac.config.redis.RedisCacheUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CacheRepository {

    private final RedisCacheUtil redisCacheUtil;

    public void setDataExpire(String key, Object value, long duration) {
        redisCacheUtil.setDataExpire(key, value, duration);
    }

    public Object getData(String key) {
        return redisCacheUtil.getData(key);
    }

    public void deleteData(String key) {
        redisCacheUtil.deleteData(key);
    }
}
package lab.redisprac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.redisprac.config.redis.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {

    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper;

    public void cacheData(String key, Object data, long timeoutSeconds) {
        try {
            redisUtil.setDataExpire(key, data, timeoutSeconds);
            log.info("Data cached successfully for key: {}", key);
        } catch (Exception e) {
            log.error("Error caching data: {}", key, e);
            throw new RuntimeException("Cache operation failed", e);
        }
    }

    public <T> Optional<T> getCachedData(String key, Class<T> type) {
        try {
            Object rawData = redisUtil.getData(key);
            if (rawData == null) return Optional.empty();

            T data = objectMapper.convertValue(rawData, type);
            log.info("레디스에 있는 멤버 찾음 : {}", data);
            return Optional.of(data);
        } catch (Exception e) {
            log.error("Error retrieving cached data for key: {}", key, e);
            return Optional.empty();
        }
    }

    public void deleteCachedData(String key) {
        try {
            redisUtil.deleteData(key);
            log.info("Cache deleted successfully for key: {}", key);
        } catch (Exception e) {
            log.error("Error deleting cached data for key: {}", key, e);
            throw new RuntimeException("Cache deletion failed", e);
        }
    }
}
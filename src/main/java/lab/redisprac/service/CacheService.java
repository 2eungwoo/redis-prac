package lab.redisprac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.redisprac.exception.CacheOperationException;
import lab.redisprac.repository.CacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {

    private final CacheRepository cacheRepository;
    private final ObjectMapper objectMapper;

    public <T> T getOrLoad(String key, Class<T> type, Supplier<T> loader, long timeoutSeconds) {
        return getCachedData(key, type).orElseGet(() -> {
            T data = loader.get();
            log.info("Cache miss for key: '{}'. Loading data from source and caching.", key);
            cacheData(key, data, timeoutSeconds);
            return data;
        });
    }

    public void cacheData(String key, Object data, long timeoutSeconds) {
        try {
            cacheRepository.setDataExpire(key, data, timeoutSeconds);
            log.debug("Data cached successfully for key: {}", key);
        } catch (Exception e) {
            throw new CacheOperationException("Error caching data for key: " + key, e);
        }
    }

    public <T> Optional<T> getCachedData(String key, Class<T> type) {
        Object rawData;
        try {
            rawData = cacheRepository.getData(key);
        } catch (Exception e) {
            throw new CacheOperationException("Error retrieving data from cache for key: " + key, e);
        }

        if (rawData == null) {
            log.debug("Cache miss for key: '{}'", key);
            return Optional.empty();
        }

        try {
            T data = objectMapper.convertValue(rawData, type);
            log.debug("Cache hit for key: '{}'", key);
            return Optional.of(data);
        } catch (IllegalArgumentException e) {
            throw new CacheOperationException("Failed to deserialize cached data for key: " + key, e);
        }
    }

    public void deleteCachedData(String key) {
        try {
            cacheRepository.deleteData(key);
            log.debug("Cache deleted successfully for key: {}", key);
        } catch (Exception e) {
            throw new CacheOperationException("Error deleting cached data for key: " + key, e);
        }
    }
}

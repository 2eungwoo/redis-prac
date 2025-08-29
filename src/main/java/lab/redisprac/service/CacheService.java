package lab.redisprac.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import lab.redisprac.repository.CacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CacheService {

    private final CacheRepository cacheRepository;
    private final ObjectMapper objectMapper;

    public void cacheData(String key, Object data, long timeoutSeconds) {
        try {
            cacheRepository.setDataExpire(key, data, timeoutSeconds);
            log.info("Data cached successfully for key: {}", key);
        } catch (Exception e) {
            log.error("Error caching data: {}", key, e);
            throw new RuntimeException("Cache operation failed", e);
        }
    }

    public <T> Optional<T> getCachedData(String key, Class<T> type) {
        try {
            Object rawData = cacheRepository.getData(key);
            if (rawData == null) {
                return Optional.empty();
            }

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
            cacheRepository.deleteData(key);
            log.info("Cache deleted successfully for key: {}", key);
        } catch (Exception e) {
            log.error("Error deleting cached data for key: {}", key, e);
            throw new RuntimeException("Cache deletion failed", e);
        }
    }
}

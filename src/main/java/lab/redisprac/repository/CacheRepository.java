package lab.redisprac.repository;

public interface CacheRepository {

    void setDataExpire(String key, Object value, long duration);

    Object getData(String key);

    void deleteData(String key);
}

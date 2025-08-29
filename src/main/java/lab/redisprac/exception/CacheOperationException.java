package lab.redisprac.exception;

public class CacheOperationException extends RuntimeException {

    public CacheOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package kodlamaio.northwind.cache;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * temporary cache in memory, not persistent, can replace with redis
 */
@Component
public class ResultCache {

    private static Map<String, String> cache = new ConcurrentHashMap<>();

    public void set(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.get(key);
    }

    public String delete(String key) {
        return cache.remove(key);
    }

    public boolean contains(String key) {
        return cache.containsKey(key);
    }

}

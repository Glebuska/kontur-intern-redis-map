package ru.gnkoshelev.kontur.intern.redis.map;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Gregory Koshelev
 */
public class RedisMap implements Map<String,String> {
    private Jedis jedis;

    private long id;

    private int size = 0;

    public RedisMap(){
        jedis = new Jedis("localhost");
        id = System.nanoTime();
        while (jedis.get("id:" + id) != null){
            id = System.nanoTime();
        }
        jedis.set("id:" + id, String.valueOf(id));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        if (size != 0) {
            Set<String> keys = keySet();
            for (String key : keys) {
                if (get(key) != null) return true;
            }
        }
        return false;
    }

    @Override
    public String get(Object key) {
        //TODO add array support
        return jedis.get(id + key.toString());
    }

    @Override
    public String put(String key, String value) {
        String val = get(key);
        if (val == null){
            size++;
        }
        jedis.set(id + key, value);
        return val;
    }

    @Override
    public String remove(Object key) {
        String val = get(key);
        if (val != null){
            jedis.del(key.toString());
            size--;
        }
        return val;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
    }

    @Override
    public void clear() {
        Set<String> keys = keySet();
        for (String key : keys) {
            remove(key);
        }
    }

    @Override
    public Set<String> keySet() {
        Set <String> keys = jedis.keys(id + "*");
        int len = String.valueOf(id).length();
        Stream <String> a = keys
                .stream()
                .map(i -> i.substring(len));
        return a.collect(Collectors.toSet());
    }

    @Override
    public Collection<String> values() {
        Collection<String> values = new ArrayList<>();
        String value;
        if (size != 0) {
            Set<String> keys = keySet();
            for (String key : keys) {
                value = get(key);
                if (value != null) values.add(value);
            }
        }
        return values;
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        throw new UnsupportedOperationException();
    }
}

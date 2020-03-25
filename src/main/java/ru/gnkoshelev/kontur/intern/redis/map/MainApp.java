package ru.gnkoshelev.kontur.intern.redis.map;

import org.apache.camel.main.Main;
import redis.clients.jedis.*;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.util.*;

import java.util.Map;


public class MainApp {

    private static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxIdle(128);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }


    public static void main(String... args) throws Exception {
//        final JedisPoolConfig poolConfig = buildPoolConfig();
//        JedisPool jedisPool = new JedisPool(poolConfig, "localhost");
        Jedis jedis = new Jedis("localhost" );

        Map<String, String> map1 = new RedisMap();
        Map<String, String> map2 = new RedisMap();

        map1.put("one", "1");

        map2.put("one", "ONE");
        map2.put("two", "TWO");

//        Set<String> matchingKeys = new HashSet<>();
//        ScanParams params = new ScanParams();
//        params.match("1*");
//
//        try(Jedis jedis = jedisPool.getResource()) {
//            String nextCursor = "0";
//
//            do {
//                ScanResult<String> scanResult = jedis.scan(nextCursor, params);
//                List<String> keys = scanResult.getResult();
//                nextCursor = scanResult.getCursor();
//
//                matchingKeys.addAll(keys);
//
//            } while(!nextCursor.equals("0"));
//
//            if (matchingKeys.size() == 0) {
//                return;
//            }
//
//            jedis.del(matchingKeys.toArray(new String[matchingKeys.size()]));
//        }

        System.out.println(map2.values());
        jedis.flushAll();
    }

}


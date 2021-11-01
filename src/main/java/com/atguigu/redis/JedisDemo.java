package com.atguigu.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Set;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/11/1 12:24
 * @description TODO
 */
public class JedisDemo {
    public static void main(String[] args) {
        // 创建Jedis对象
        Jedis jedis = new Jedis("172.26.208.1",6379);

        // 测试
        String ping = jedis.ping();
        System.out.println("ping = " + ping);
    }

    // 测试
    @Test
    public void demo1() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("172.26.208.1",6379);

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key = " + key);
        }

        jedis.set("ka", "ka1");
        jedis.set("kb", "kab1");
        jedis.set("kc", "kac1");
        jedis.set("kd", "kad1");

        keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key = " + key);
        }
    }

    @Test
    public void addKeys() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("172.26.208.1",6379);

        for (int i = 0; i < 100; i++) {
            String substring = UUID.randomUUID().toString().substring(0, 8);
            jedis.set(substring, substring);

        }

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key = " + key);
        }
    }

    @Test
    public void getKeys() {
        // 创建Jedis对象
        Jedis jedis = new Jedis("172.26.208.1",6379);

        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key = " + key);
        }
    }
}

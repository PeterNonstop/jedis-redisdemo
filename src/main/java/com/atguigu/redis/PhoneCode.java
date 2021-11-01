package com.atguigu.redis;

import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * To change it use File | Settings | Editor | File and Code Templates.
 *
 * @author Peter
 * @date 2021/11/1 14:27
 * @description TODO
 */
public class PhoneCode {
    public static void main(String[] args) {
        // 模拟验证码发送
        //verifyCode("13510742037");

        getRedisCode("13510742037","895072");
    }

    /**
     * 验证码检验
     *
     * @param phone
     * @param code
     */
    public static void getRedisCode(String phone, String code) {
        // 从Redis获取验证码
        Jedis jedis = new Jedis("172.26.208.1", 6379);

        // 验证码key
        String codeKey = "VerifyCode" + phone + ":code";
        String redisCode = jedis.get(codeKey);

        // 判断redisCode是否存在
        if (StringUtils.isEmpty(redisCode)) {
            System.out.println("验证码不存在，请重新输入");
            jedis.close();
            return;
        }

        // 判断
        if (redisCode.equals(code)) {
            System.out.println("验证成功！");
        } else {
            System.out.println("验证码错误，请重新输入");
        }

        jedis.close();
    }

    /**
     * 每个手机号只能发送3次,验证码放到Redis中，过期时间2分钟
     *
     * @param phone
     *
     */
    public static void verifyCode(String phone) {
        // 创建Jedis对象
        Jedis jedis = new Jedis("172.26.208.1", 6379);

        // 拼接Key
        // 手机发送次数
        String countKey = "VerifyCode" + phone + ":count";

        // 验证码key
        String codeKey = "VerifyCode" + phone + ":code";

        // 每个手机每天只能发送3次
        String count = jedis.get(countKey);
        if (count == null) {
            // 没有发送次数，第一次发送
            // 设置发送次数为1
            jedis.setex(countKey, 24 * 60 * 60, "1");
        } else if (Integer.parseInt(count) <= 2) {
            // 发送次数加1
            jedis.incr(countKey);
        } else if (Integer.parseInt(count) > 2) {
            // 发送超过3次，不能再发送
            System.out.println("今天发送次数已用完，请明天请试。");
            jedis.close();
            return;
        }

        // 把验证码放到Redis中
        String vCode = getCode();
        jedis.setex(codeKey, 120, vCode);
        jedis.close();
    }

    /**
     * 生机6位随机验证码
     *
     * @return
     */
    public static String getCode() {
        Random random = new Random();
        String code = "";
        for (int i = 0; i < 6; i++) {
            int i1 = random.nextInt(10);
            code += i1;
        }

        System.out.println("code = " + code);
        return code;
    }
}

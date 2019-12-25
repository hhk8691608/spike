package com.ace.study.spike.cache;


import com.ace.study.spike.VO.cache.UserVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CacheApplicationTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisTest() {
        // redis存储数据
        String key = "name";
        redisTemplate.opsForValue().set(key, "yukong");
        // 获取数据
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("获取缓存中key为" + key + "的值为：" + value);

        UserVo user = new UserVo();
        user.setUsername("yukong");
        user.setSex(18);
        user.setId(1L);
        String userKey = "yukong";
        redisTemplate.opsForValue().set(userKey, user);
        UserVo newUser = (UserVo) redisTemplate.opsForValue().get(userKey);
        System.out.println("获取缓存中key为" + userKey + "的值为：" + newUser);

    }

}

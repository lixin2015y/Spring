package com.lee.controller;

import com.lee.util.IOUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("limit")
@RestController
public class LimitController {

    @Resource
    redis.clients.jedis.JedisCluster jedisCluster;


    String limitScript = IOUtil.loadJarFile(this.getClass().getClassLoader(), "limitRate.lua");


    @GetMapping("limit/{id}")
    String limit(@PathVariable String id) {
        List<String> keys = new ArrayList<>();
        keys.add("acquire");
        List<String> args = new ArrayList<>();
        args.add(id);
        args.add("1");
        Object eval = jedisCluster.eval(limitScript, keys, args);

        System.out.println(eval);
        return (Integer)eval > 0 ? "通过" : "限流";

    }

}

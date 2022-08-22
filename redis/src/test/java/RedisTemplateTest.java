import com.lee.App;
import com.lee.config.JedisClusterPlus;
import com.lee.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.KeyBoundCursor;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.util.JedisClusterCRC16;
import sun.applet.Main;
import sun.java2d.pipe.SolidTextRenderer;

import javax.annotation.Resource;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RedisTemplateTest {

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;

    @Resource
    JedisClusterPlus jedisClusterPlus;

    @Test
    public void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("lixin", "李新");
        System.out.println(valueOperations.get("lixin"));
    }

    @Test
    public void test2() {
        User user = new User("李新", 24);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user", user);
        System.out.println(valueOperations.get("user"));

    }

    @Test
    public void test3() {

        ValueOperations valueOperations = redisTemplate.opsForValue();


        for (int i = 0; i < 100000; i++) {
            valueOperations.set("key" + i, "value" + i);
        }
    }

    @Test
    public void test4() {
        RedisClusterNode redisClusterNode = new RedisClusterNode("172.16.2.218", 9201);

        Set keys = redisTemplate.opsForCluster().keys(redisClusterNode, "key*");
        System.out.println(keys.size());
    }

    @Test
    public void test5() {
        Object eval = jedisClusterPlus.eval("return ARGV[1]", 1, "acquire", "acquire", "1");
        System.out.println(eval);
    }

    @Test
    public void test6() {
        Map<JedisPool, List<String>> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            String key = "yyyyy" + i;
            int slot = JedisClusterCRC16.getSlot(key);
            System.out.println("slot = " + slot);
            JedisPool jedisPool = jedisClusterPlus.getConnectionHandler().getJedisPoolFromSlot(slot);

            if (map.containsKey(jedisPool)) {
                List<String> strings = map.get(jedisPool);
                strings.add(key);
            } else {
                List<String> list = new ArrayList<>();
                list.add(key);
                map.put(jedisPool, list);
            }
            List<String> list = map.getOrDefault(jedisPool, new ArrayList<>());
            list.add(key);
        }
        for (JedisPool jedisPool : map.keySet()) {
            try (Jedis jedis = jedisPool.getResource()) {
                Pipeline pipelined = jedis.pipelined();
                List<String> strings = map.get(jedisPool);
                for (String string : strings) {
                    pipelined.lpush(string, string);
                }
                List<Object> objects = pipelined.syncAndReturnAll();
                System.out.println(objects);
            }
            System.out.println("======================");
        }
    }

    // fluash all
    @Test
    public void test7() {
        Map<String, JedisPool> clusterNodes = jedisClusterPlus.getClusterNodes();
        for (Map.Entry<String, JedisPool> jedisPoolEntry : clusterNodes.entrySet()) {
            JedisPool jedisPool = jedisPoolEntry.getValue();
            try (Jedis jedis = jedisPool.getResource()){
                try {
                    jedis.flushAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(jedisPoolEntry.getKey() + "清理完成");
            }
        }
    }

}

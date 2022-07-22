import com.lee.App;
import com.lee.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisClusterNode;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RedisTemplateTest {

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;

    @Resource
    JedisCluster jedisCluster;

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
        Object eval = jedisCluster.eval("return ARGV[1]", 1, "acquire", "acquire", "1");
        System.out.println(eval);
    }

}

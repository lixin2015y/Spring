import com.lee.App;
import com.lee.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class RedisTemplateTest {

    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate redisTemplate;

    @Test
    public void test(){

        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("lixin", "李新");
        System.out.println(valueOperations.get("lixin"));

    }

    @Test
    public void test2(){
        User user = new User("李新", 24);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("user", user);
        System.out.println(valueOperations.get("user"));

    }

}

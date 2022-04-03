import com.lee.App;
import com.lee.dao.UserRepository;
import com.lee.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ShardingJdbcSpringbootTest {


    @Resource
    UserRepository userRepository;

    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            User dto = new User();

            dto.setName("user_" + i);

            //增加用户
            userRepository.insert(dto);
        }
    }
}

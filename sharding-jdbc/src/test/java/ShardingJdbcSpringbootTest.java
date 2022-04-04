import com.lee.App;
import com.lee.dao.DictRepository;
import com.lee.dao.UserRepository;
import com.lee.dto.Dict;
import com.lee.dto.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ShardingJdbcSpringbootTest {


    @Resource
    UserRepository userRepository;

    @Resource
    DictRepository dictRepository;

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            User dto = new User();
            dto.setUserId(i * 1000 + i + 1);
            dto.setName("user_" + i);
            //增加用户
            userRepository.insert(dto);
        }
    }


    @Test
    public void test2() {
        List<User> users = userRepository.selectUser(1000l, 5000l);
        System.out.println(users);
    }

    @Test
    public void test3() {
        List<User> users = userRepository.selectUserByIdAndName(1000l, 5000l, "user_3");
        System.out.println(users);
    }

    @Test
    public void test4() {
        for (int i = 0; i < 10; i++) {
            Dict dto = new Dict();
            dto.setDictId(i * 1000 + i + 1);
            dto.setName("dict_name" + i);
            //增加用户
            dictRepository.insert(dto);
        }

    }
}

import com.lee.App;
import com.lee.service.StudentService;
import com.lee.service.TeacherService;
import com.lee.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MySpringBootTest {

    @Resource
    UserService userService;


    @Resource
    StudentService studentService;

    @Resource
    TeacherService teacherService;

    @Test
    public void test() {
        userService.deleteById(1);
        studentService.deleteById(1);
        teacherService.deleteById(1);
    }

}

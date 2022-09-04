import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.compensate.api.CompensationCallBack;
import com.compensate.supports.CallBackResponse;
import com.compensate.supports.DubboUtil;
import com.compensate.supports.SpringContextHolder;
import com.lee.App;
import com.lee.service.DemoService;
import com.lee.service.StudentService;
import com.lee.service.TeacherService;
import com.lee.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


public class MySpringBootTest {

    ApplicationConfig applicationConfig = new ApplicationConfig();

    RegistryConfig registryConfig = new RegistryConfig();


    @Before
    public void before() {
        registryConfig.setAddress("zookeeper://172.16.2.218:2181");
        applicationConfig.setName("consumer");
    }

    @Test
    public void test() {
        ReferenceConfig<CompensationCallBack> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(CompensationCallBack.class);
        referenceConfig.setGroup("lixin-group");
        referenceConfig.setVersion("0.0.1");
        CompensationCallBack compensationCallBack = referenceConfig.get();
        CallBackResponse callBackResponse = compensationCallBack.callBack("[\"lixin\"]");
        System.out.println(callBackResponse);
    }

    @Test
    public void test3() {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setInterface("com.compensate.api.CompensationCallBack");
        reference.setGeneric(true);
        reference.setGroup("lixin-group");
        reference.setVersion("0.0.1");

        GenericService genericService = reference.get();
        Object result = genericService.$invoke("callBack", new String[] {"java.lang.String"}, new Object[] {"[\"lixin\"]"});
        System.out.println(result);


    }
}

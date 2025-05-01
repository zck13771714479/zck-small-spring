import beans.IUserService;
import com.zck.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class APITest {
    @Test
    public void testProperty() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-property.xml");
        IUserService userService = context.getBean("userService", IUserService.class);
        System.out.println(userService);
    }

    @Test
    public void test_scan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-scan.xml");
        IUserService userService = applicationContext.getBean("userService", IUserService.class);
        System.out.println("测试结果：" + userService.queryUserInfo());
    }
}

import beans.IUserService;
import com.zck.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

public class APITest {

    @Test
    public void test() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        IUserService userService = context.getBean("userService", IUserService.class);
        System.out.println(userService.queryUserInfo());
    }
}

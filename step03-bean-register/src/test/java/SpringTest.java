import com.zck.config.BeanDefinition;
import com.zck.factory.DefaultListableBeanFactory;
import org.junit.Test;

public class SpringTest {
    @Test
    public void beanRegisterTest(){
        //构建beanDefinition
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        //创建容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //注册beanDefinition
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        //第一次使用bean
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        userService1.queryUserInfo();
        //第二次使用bean
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        userService2.queryUserInfo();
    }
}

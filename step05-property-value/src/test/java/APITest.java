import com.zck.PropertyValue;
import com.zck.PropertyValues;
import com.zck.config.BeanDefinition;
import com.zck.config.BeanReference;
import com.zck.factory.DefaultListableBeanFactory;
import org.junit.Test;

public class APITest {
    @Test
    public void testPropertyValue() {
        //创建容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //注册userDAO
        BeanDefinition userDAODefinition = new BeanDefinition(UserDAO.class);
        beanFactory.registerBeanDefinition("userDAO", userDAODefinition);
        //准备userService的属性
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("userId", "user003"));
        propertyValues.addPropertyValue(new PropertyValue("userDAO", new BeanReference("userDAO")));
        //注册userService
        beanFactory.registerBeanDefinition("userService", new BeanDefinition(UserService.class, propertyValues));
        //使用userService
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUsernameById(userService.getUserId());
    }
}

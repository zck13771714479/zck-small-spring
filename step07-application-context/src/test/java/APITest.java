import bean.UserService;
import com.zck.beans.factory.support.DefaultListableBeanFactory;
import com.zck.beans.factory.xml.XmlBeanDefinitionReader;
import com.zck.context.support.ClassPathXmlApplicationContext;
import common.MyBeanFactoryPostProcessor;
import common.MyBeanPostProcessor;
import org.junit.Test;

public class APITest {
    @Test
    public void withContextNoPostProcessor() {
        //创建容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        //加载所有的bean
        context.refresh();
        //使用bean
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void withContextPostProcessor() {
        //创建容器
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");
        //加载所有的bean
        context.refresh();
        //使用bean
        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService.queryUserInfo());
    }

    @Test
    public void noContextYesPostProcessor() {
        //创建容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //加载beanDefinition
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        beanDefinitionReader.loadBeanDefinition("classpath:springPostProcessor.xml");
        //手动实例化beanFactory后处理器,并对beanDefinition进行修改
        MyBeanFactoryPostProcessor myBeanFactoryPostProcessor = (MyBeanFactoryPostProcessor) beanFactory.getBean("myBeanFactoryPostProcessor");
        myBeanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        //手动获取userService的bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        //手动获取bean后处理器，对bean进行后处理
        MyBeanPostProcessor beanPostProcessor = (MyBeanPostProcessor) beanFactory.getBean("myBeanPostProcessor");
        beanPostProcessor.postProcessBeforeInitialization(userService, "userService");
        beanPostProcessor.postProcessAfterInitialization(userService, "userService");
        //使用bean
        System.out.println(userService.queryUserInfo());
    }
}

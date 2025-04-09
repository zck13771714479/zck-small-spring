import cn.hutool.core.io.IoUtil;
import com.zck.beans.factory.support.DefaultListableBeanFactory;
import com.zck.beans.factory.xml.XmlBeanDefinitionReader;
import com.zck.core.io.DefaultResourceLoader;
import com.zck.core.io.Resource;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class APITest {
    private DefaultResourceLoader resourceLoader;

    @Before
    public void initResourceLoader() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void testLoadClassPath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testLoadFile() throws IOException {
        Resource resource = resourceLoader.getResource("src/main/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/blob/main/small-spring-step-05/src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void testLoadXML() throws IOException {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        Resource resource = resourceLoader.getResource("classpath:bean.xml");
        InputStream inputStream = resource.getInputStream();
        //扫描加载bean
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, resourceLoader);
        xmlBeanDefinitionReader.loadBeanDefinition(resource);

        UserService userService = (UserService) beanFactory.getBean("userService");
        String name = userService.queryUsernameById("user002");
        System.out.println(name);
    }

}

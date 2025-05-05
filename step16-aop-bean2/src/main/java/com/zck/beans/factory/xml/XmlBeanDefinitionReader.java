package com.zck.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import com.zck.beans.BeansException;
import com.zck.beans.PropertyValue;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanDefinitionRegistry;
import com.zck.beans.factory.config.BeanReference;
import com.zck.beans.factory.support.AbstractBeanDefinitionReader;
import com.zck.context.annotation.ClassPathBeanDefinitionScanner;
import com.zck.core.io.DefaultResourceLoader;
import com.zck.core.io.Resource;
import com.zck.core.io.ResourceLoader;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 读取xml文件，转化为beanDefinition
 * xml bean示例
 * <bean id="userDao" class="com.example.UserDao"/>
 * <bean id="userService" class="com.example.UserService">
 * <property name="userDao" ref="userDao"/>
 * <property name="name" value="Bob"/>
 * </bean>
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 加载资源转化为beanDefinition
     *
     * @param resource
     * @throws BeansException
     */
    @Override
    public void loadBeanDefinition(Resource resource) throws BeansException {
        try (InputStream inputStream = resource.getInputStream()) {
            doLoadBeanDefinitions(inputStream);
        } catch (IOException | ClassNotFoundException | DocumentException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }


    /**
     * 加载资源转化为beanDefinition
     *
     * @param resources
     * @throws BeansException
     */
    @Override
    public void loadBeanDefinition(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinition(resource);
        }
    }

    /**
     * 加载资源转化为beanDefinition
     *
     * @param location
     * @throws BeansException
     */
    @Override
    public void loadBeanDefinition(String location) throws BeansException {
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinition(resource);
    }

    /**
     * 执行加载beanDefinition
     *
     * @param inputStream
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException, DocumentException {
        //读取xml文件
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();

        // 解析 context:component-scan 标签，扫描包中的类并提取相关信息，用于组装 BeanDefinition
        Element componentScan = root.element("component-scan");
        if (null != componentScan) {
            String scanPath = componentScan.attributeValue("base-package");
            if (StrUtil.isEmpty(scanPath)) {
                throw new BeansException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }
        List<Element> beanList = root.elements("bean");
        for (Element beanElement : beanList) {
            //获取bean的基本信息
            String id = beanElement.attributeValue("id");
            String className = beanElement.attributeValue("class");
            String name = beanElement.attributeValue("name");
            String initMethod = beanElement.attributeValue("init-method");
            String destroyMethodName = beanElement.attributeValue("destroy-method");
            String scope = beanElement.attributeValue("scope");
            Class<?> clazz = Class.forName(className);
            //设置bean的名字,id优先级高于name
            String beanName = StrUtil.isEmpty(id) ? name : id;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethod);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            if (StrUtil.isNotEmpty(scope)) {
                beanDefinition.setScope(scope);
            }
            List<Element> propertyList = beanElement.elements("property");
            for (Element property : propertyList) {
                // 解析标签：property
                String attrName = property.attributeValue("name");
                String attrValue = property.attributeValue("value");
                String attrRef = property.attributeValue("ref");
                // 获取属性值：引入对象、值对象
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }

    }

    private void scanPackage(String scanPath) {
        String[] split = scanPath.split(",");
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(split);
    }
}

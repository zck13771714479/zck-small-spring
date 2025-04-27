package com.zck.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.zck.beans.BeansException;
import com.zck.beans.PropertyValue;
import com.zck.beans.PropertyValues;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanDefinitionRegistry;
import com.zck.beans.factory.config.BeanReference;
import com.zck.beans.factory.support.AbstractBeanDefinitionReader;
import com.zck.core.io.DefaultResourceLoader;
import com.zck.core.io.Resource;
import com.zck.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;


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
        } catch (IOException | ClassNotFoundException e) {
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
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        //读取xml文件
        Document document = XmlUtil.readXML(inputStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            //跳过非bean元素
            if (!(childNodes.item(i) instanceof Element)) {
                continue;
            }
            if (!childNodes.item(i).getNodeName().equals("bean")) {
                continue;
            }
            //获取bean的基本信息
            Element beanElement = (Element) childNodes.item(i);
            String id = beanElement.getAttribute("id");
            String className = beanElement.getAttribute("class");
            String name = beanElement.getAttribute("name");
            String initMethodName = beanElement.getAttribute("init-method");
            String destroyMethodName = beanElement.getAttribute("destroy-method");
            String scope = beanElement.getAttribute("scope");


            Class<?> clazz = Class.forName(className);
            //设置bean的名字,id优先级高于name
            String beanName = StrUtil.isEmpty(id) ? name : id;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }
            //获取属性值
            NodeList propertyChildNodes = beanElement.getChildNodes();
            PropertyValues propertyValues = new PropertyValues();
            for (int j = 0; j < propertyChildNodes.getLength(); j++) {
                //注入属性值
                if (!(propertyChildNodes.item(j) instanceof Element)) {
                    continue;
                }
                if (!propertyChildNodes.item(j).getNodeName().equals("property")) {
                    continue;
                }
                Element propertyElement = (Element) propertyChildNodes.item(j);
                String fieldName = propertyElement.getAttribute("name");
                String fieldValue = propertyElement.getAttribute("value");
                String ref = propertyElement.getAttribute("ref");
                if (StrUtil.isEmpty(ref)) {
                    propertyValues.addPropertyValue(new PropertyValue(fieldName, fieldValue));
                    continue;
                }
                propertyValues.addPropertyValue(new PropertyValue(fieldName, new BeanReference(ref)));
            }
            //创建并注册beanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz, propertyValues);
            //设置初始化和销毁的钩子
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            //设置bean的scope
            beanDefinition.setScope(scope);
            BeanDefinitionRegistry registry = getRegistry();
            registry.registerBeanDefinition(beanName, beanDefinition);
        }
    }
}

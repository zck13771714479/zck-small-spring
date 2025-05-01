package com.zck.beans.factory;

import com.zck.beans.BeansException;
import com.zck.beans.PropertyValue;
import com.zck.beans.PropertyValues;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanFactoryPostProcessor;
import com.zck.core.io.DefaultResourceLoader;
import com.zck.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    /**
     * Default placeholder prefix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: {@value}
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    private String location;

    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     *
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            //读取配置文件
            DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
            Resource resource = defaultResourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            //将${aaaa}替换成配置文件里的值
            String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
                PropertyValues propertyValues = beanDefinition.getPropertyValues();
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    Object value = propertyValue.getValue();
                    if (value instanceof String) {
                        int startIndex = ((String) value).indexOf(DEFAULT_PLACEHOLDER_PREFIX);
                        int endIndex = ((String) value).indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
                        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                            String propKey = ((String) value).substring(startIndex + 2, endIndex);
                            String propValue = properties.getProperty(propKey);
                            propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), propValue));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }
}

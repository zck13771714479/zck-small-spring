package com.zck.beans.factory;

import com.zck.beans.BeansException;
import com.zck.beans.PropertyValue;
import com.zck.beans.PropertyValues;
import com.zck.beans.factory.config.BeanDefinition;
import com.zck.beans.factory.config.BeanFactoryPostProcessor;
import com.zck.core.io.DefaultResourceLoader;
import com.zck.core.io.Resource;
import com.zck.utils.StringValueResolver;

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
                        //解析模板字符串
                        String propValue = this.resolvePlaceholder((String) value, properties);
                        propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(), propValue));
                    }
                }
            }

            // 向容器中添加字符串解析器，供解析@Value注解使用
            StringValueResolver resolver = new PlaceholderResolvingStringValueResolver(properties);
            beanFactory.addEmbeddedValueResolver(resolver);

        } catch (IOException e) {
            throw new BeansException("Could not load properties", e);
        }
    }

    private String resolvePlaceholder(String value, Properties properties) {
        StringBuilder buffer = new StringBuilder(value);
        int startIndex = value.indexOf(DEFAULT_PLACEHOLDER_PREFIX);
        int endIndex = value.indexOf(DEFAULT_PLACEHOLDER_SUFFIX);
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            String propKey = value.substring(startIndex + 2, endIndex);
            String propValue = properties.getProperty(propKey);
            buffer.replace(startIndex, endIndex + 1, propValue);
        }
        return buffer.toString();
    }


    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final Properties properties;

        public PlaceholderResolvingStringValueResolver(Properties properties) {
            this.properties = properties;
        }

        /**
         * 解析模板字符串，获取值
         *
         * @param value
         * @return
         */
        @Override
        public String resolveStringValue(String value) {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(value, properties);
        }
    }


}

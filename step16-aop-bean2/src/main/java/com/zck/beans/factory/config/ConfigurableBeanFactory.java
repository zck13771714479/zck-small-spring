package com.zck.beans.factory.config;


import com.zck.beans.factory.HierarchicalBeanFactory;
import com.zck.utils.StringValueResolver;

/**
 * Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the
 * interface.
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 添加bean后处理器
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 注入值解析器，@Value解析
     * @param resolver
     */
    void addEmbeddedValueResolver(StringValueResolver resolver);

    /**
     * @Value解析模板值
     * @param value
     * @return
     */
    String resolveEmbeddedValue(String value);
}

package com.zck.beans.factory.config;

/**
 * beanDefinition注册器
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}

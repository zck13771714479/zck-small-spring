package com.zck.factory;

import com.zck.config.BeanDefinition;

/**
 * beanDefinition注册器
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}

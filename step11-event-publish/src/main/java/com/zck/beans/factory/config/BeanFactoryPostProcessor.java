package com.zck.beans.factory.config;

import com.zck.beans.BeansException;
import com.zck.beans.factory.ConfigurableListableBeanFactory;

/**
 * bean工厂后处理器，用于bean实例化前的处理
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}

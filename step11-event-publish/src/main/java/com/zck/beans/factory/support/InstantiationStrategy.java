package com.zck.beans.factory.support;



import com.zck.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 初始化bean对象的策略接口，例如反射初始化，使用cglib初始化等
 */
public interface InstantiationStrategy {


    /**
     * 初始化bean
     *
     * @param beanName
     * @param beanDefinition
     * @param constructor    选用的构造函数
     * @param args           构造函数需要的参数
     * @return
     */
    Object instantiate(String beanName, BeanDefinition beanDefinition, Constructor<?> constructor, Object[] args);

}
